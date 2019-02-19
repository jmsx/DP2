
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import domain.Actor;
import domain.Brotherhood;
import domain.FloatProcession;
import domain.Folder;
import domain.Message;
import domain.Procession;

@Service
@Transactional
public class ProcessionService {

	@Autowired
	private ProcessionRepository	processionRepository;

	@Autowired
	private BrotherhoodService		brotherhoodService;


	public Procession create() {
		final Procession procession = new Procession();

		final Collection<FloatProcession> floats = new ArrayList<>();
		procession.setFloatProcessions(floats);

		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		procession.setBrotherhood(brotherhood);

		return procession;

	}

	public Collection<Procession> findAll() {
		final Collection<Procession> res = this.processionRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public Procession findOne(final int processionId) {
		final Procession res = this.processionRepository.findOne(processionId);

		return res;
	}

	public Folder save(final Folder f, final Actor a) {
		Assert.notNull(f);
		Assert.notNull(a);

		Folder saved;
		final boolean bool = this.checkForSpamWords(f);

		if (bool)
			a.setSpammer(true);

		if (f.getId() == 0)
			saved = this.folderRepository.save(f);
		else {
			final Collection<Folder> fs = this.findAllByUserId(a.getUserAccount().getId());
			Assert.isTrue(fs.contains(f));
			saved = this.folderRepository.save(f);
		}
		return saved;
	}

	private boolean checkForSpamWords(final Folder f) {
		final Collection<String> words = new ArrayList<>();
		words.add(f.getName());

		return this.configurationParametersService.checkForSpamWords(words);
	}

	public void delete(final Folder f) {
		Assert.notNull(f);
		Assert.isTrue(!f.getIsSystemFolder());
		Assert.isTrue(f.getId() != 0);

		final Actor principal = this.actorService.findByPrincipal();
		final Collection<Folder> fs = this.findAllByUserId(principal.getUserAccount().getId());
		final Collection<Message> ms = this.messageService.findAllByFolderIdAndUserId(f.getId(), principal.getUserAccount().getId());
		Assert.isTrue(fs.contains(f));

		if (!ms.isEmpty())
			//El delete de folder falla por este clear, ya que se le pasa al metodo deleteAll() una carpeta vacia y falla el assert de que no este vacia
			//f.getMessages().clear();
			this.messageService.deleteAll(ms, f);

		this.folderRepository.delete(f);
		this.actorService.update(principal);
	}

	public Collection<Folder> setFoldersByDefault(final Actor actor) {
		final Collection<Folder> folders = new ArrayList<Folder>();
		final Collection<Message> messages = new ArrayList<Message>();

		final Folder inbox = this.create();
		inbox.setName("In box");
		inbox.setIsSystemFolder(true);
		inbox.setActor(actor);
		inbox.setMessages(messages);
		folders.add(inbox);

		final Folder outbox = this.create();
		outbox.setName("Out box");
		outbox.setIsSystemFolder(true);
		outbox.setActor(actor);
		outbox.setMessages(messages);
		folders.add(outbox);

		final Folder trash = this.create();
		trash.setName("Trash box");
		trash.setIsSystemFolder(true);
		trash.setActor(actor);
		trash.setMessages(messages);
		folders.add(trash);

		final Folder spam = this.create();
		spam.setName("Spam box");
		spam.setIsSystemFolder(true);
		spam.setActor(actor);
		spam.setMessages(messages);
		folders.add(spam);

		final Folder notification = this.create();
		notification.setName("Notification box");
		notification.setIsSystemFolder(true);
		notification.setActor(actor);
		notification.setMessages(messages);
		folders.add(notification);

		return this.saveAll(folders);
	}

	public Collection<Folder> saveAll(final Collection<Folder> fs) {
		Assert.notEmpty(fs);
		return this.folderRepository.save(fs);
	}

	public Collection<Folder> findAllByUserId(final int id) {
		Assert.isTrue(id != 0);

		return this.folderRepository.findAllByUserId(id);
	}

	public Folder findInboxByUserId(final int id) {
		Assert.isTrue(id != 0);

		return this.folderRepository.findInboxByUserId(id);
	}

	public Folder findOutboxByUserId(final int id) {
		Assert.isTrue(id != 0);

		return this.folderRepository.findOutboxByUserId(id);
	}

	public Folder findSpamboxByUserId(final int id) {
		Assert.isTrue(id != 0);

		return this.folderRepository.findSpamboxByUserId(id);
	}

	public Folder findTrashboxByUserId(final int id) {
		Assert.isTrue(id != 0);

		return this.folderRepository.findTrashboxByUserId(id);
	}

	public Folder findNotificationboxByUserId(final int id) {
		Assert.isTrue(id != 0);

		return this.folderRepository.findNotificationboxByUserId(id);
	}

	public Collection<Folder> findAllByMessageIdAndUserId(final int mid, final int uid) {
		Assert.isTrue(mid != 0);
		Assert.isTrue(uid != 0);

		return this.folderRepository.findAllByMessageIdAndUserId(mid, uid);
	}

}
