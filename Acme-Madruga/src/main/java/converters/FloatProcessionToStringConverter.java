
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.FloatProcession;

@Component
@Transactional
public class FloatProcessionToStringConverter implements Converter<FloatProcession, String> {

	@Override
	public String convert(final FloatProcession fProcession) {

		String result;

		if (fProcession == null)
			result = null;
		else
			result = String.valueOf(fProcession.getId());

		return result;
	}

}
