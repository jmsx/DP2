
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository				messageRepository;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private FolderService					folderService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;

	@Autowired
	private AdministratorService			administratorService;


	public Message create() {
		final Message message = new Message();
		final Collection<String> tags = new ArrayList<>();
		message.setTags(tags);

		final Date moment = new Date(System.currentTimeMillis() - 1000);
		message.setMoment(moment);

		final Actor principal = this.actorService.findByPrincipal();
		message.setSender(principal);

		return message;
	}

	public Collection<Message> findAll() {
		final Actor principal = this.actorService.findByPrincipal();
		final Collection<Message> res = this.messageRepository.findAllByUserId(principal.getUserAccount().getId());
		Assert.notNull(res);

		return res;
	}

	public Message findOne(final int id) {
		final Message res = this.messageRepository.findOne(id);

		return res;
	}

	public Collection<Message> findAllByFolderIdAndUserId(final int fid, final int uid) {
		Assert.isTrue(fid != 0);
		Assert.isTrue(uid != 0);

		return this.messageRepository.findAllByFolderIdAndUserId(fid, uid);
	}

	public Message save(final Message m) {
		Assert.notNull(m);

		return this.messageRepository.save(m);
	}

	public Message send(final Message m) {
		Assert.notNull(m);

		//Esto pasa a setearse en el create
		//final Actor sender = this.actorService.findByPrincipal();
		//m.setSender(sender);
		final Actor sender = this.actorService.findByPrincipal();
		final Folder outbox = this.folderService.findOutboxByUserId(sender.getUserAccount().getId());
		final Collection<Message> outboxMessages = outbox.getMessages();

		//Pasa a ser seteado en el create
		//final Date moment = new Date(System.currentTimeMillis() - 1000);
		//m.setMoment(moment);

		//Pasa a ser seteado en el create
		//final Collection<String> tags = new ArrayList<>();
		//m.setTags(tags);

		final boolean bool = this.checkForSpamWords(m);

		final Collection<Actor> recipients = m.getRecipients();
		Folder inbox;
		Folder spambox;

		final Message sent = this.save(m);

		outboxMessages.add(sent);
		outbox.setMessages(outboxMessages);

		if (bool) {
			sender.setSpammer(true);
			this.actorService.update(sender);

			for (final Actor r : recipients) {
				spambox = this.folderService.findSpamboxByUserId(r.getUserAccount().getId());
				final Collection<Message> spamMessages = spambox.getMessages();
				spamMessages.add(sent);
				spambox.setMessages(spamMessages);
				this.folderService.save(spambox, r);
			}
		} else
			for (final Actor r : recipients) {
				inbox = this.folderService.findInboxByUserId(r.getUserAccount().getId());
				final Collection<Message> inboxMessages = inbox.getMessages();
				inboxMessages.add(sent);
				inbox.setMessages(inboxMessages);
				this.folderService.save(inbox, r);
			}

		return sent;
	}

	public void broadcast(final Message m) {
		Assert.notNull(m);
		this.administratorService.findByPrincipal();

		final Collection<Actor> actors = this.actorService.findAll();
		m.setRecipients(actors);
		this.send(m);
	}

	public boolean checkForSpamWords(final Message m) {
		final String body = m.getBody().toLowerCase();
		final String subject = m.getSubject().toLowerCase();
		final Collection<String> strings = new ArrayList<>();
		strings.add(subject);
		strings.add(body);

		return this.configurationParametersService.checkForSpamWords(strings);
	}

	public void deleteFromFolder(final Message m, final Folder f) {
		Assert.notNull(m);
		Assert.isTrue(m.getId() != 0);
		Assert.isTrue(f.getMessages().contains(m));

		final Actor principal = this.actorService.findByPrincipal();
		final Folder trash = this.folderService.findTrashboxByUserId(principal.getUserAccount().getId());

		final Collection<Message> fms = this.findAllByFolderIdAndUserId(f.getId(), principal.getUserAccount().getId());
		final Collection<Message> tms = this.findAllByFolderIdAndUserId(trash.getId(), principal.getUserAccount().getId());
		final Collection<Folder> fs = this.folderService.findAllByMessageIdAndUserId(principal.getUserAccount().getId(), m.getId());

		final boolean bool = f.getId() == trash.getId();

		if (bool) {
			tms.remove(m);
			trash.setMessages(tms);

			for (final Folder i : fs) {
				final Collection<Message> ims = i.getMessages();
				ims.remove(m);
				i.setMessages(ims);
				this.folderService.save(i, principal);
			}

			this.folderService.save(trash, principal);

			if (this.actorService.countByMessageId(m.getId()) == 0)
				this.messageRepository.delete(m);

		} else {
			fms.remove(m);
			f.setMessages(fms);
			tms.add(m);
			trash.setMessages(tms);
			this.folderService.save(trash, principal);
			this.folderService.save(f, principal);
		}
	}

	/* Este método se llama desde FolderService y es necesario que cumpla las restricciones de un delete, de ahí el uso de un for */
	public void deleteAll(final Collection<Message> ms, final Folder f) {
		Assert.notEmpty(ms);
		for (final Message m : ms)
			this.deleteFromFolder(m, f);
	}

	public Message copyToFolder(final Message m, final Folder f) {
		Assert.notNull(m);
		Assert.notNull(f);
		Assert.isTrue(m.getId() != 0);
		Assert.isTrue(f.getId() != 0);

		final Actor principal = this.actorService.findByPrincipal();
		final Collection<Message> ms = this.findAllByFolderIdAndUserId(f.getId(), principal.getUserAccount().getId());

		ms.add(m);
		f.setMessages(ms);
		this.folderService.save(f, principal);

		return m;
	}

	public Message moveFromAToB(final Message m, final Folder a, final Folder b) {
		Assert.notNull(m);
		Assert.notNull(a);
		Assert.notNull(b);
		Assert.isTrue(m.getId() != 0);
		Assert.isTrue(a.getId() != 0);
		Assert.isTrue(b.getId() != 0);

		final Actor principal = this.actorService.findByPrincipal();
		final Collection<Message> bms = this.findAllByFolderIdAndUserId(b.getId(), principal.getUserAccount().getId());
		final Collection<Message> ams = this.findAllByFolderIdAndUserId(a.getId(), principal.getUserAccount().getId());

		ams.remove(m);
		a.setMessages(ams);
		bms.add(m);
		b.setMessages(bms);
		this.folderService.save(b, principal);
		this.folderService.save(a, principal);

		return m;
	}

}
