
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.FloatProcessionRepository;
import domain.FloatProcession;

@Component
@Transactional
public class StringToFloatProcessionConverter implements Converter<String, FloatProcession> {

	@Autowired
	private FloatProcessionRepository	floatProcessionRepository;


	@Override
	public FloatProcession convert(final String text) {

		final FloatProcession res;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.floatProcessionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
