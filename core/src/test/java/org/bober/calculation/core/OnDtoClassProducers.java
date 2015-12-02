package org.bober.calculation.core;

import org.bober.calculation.core.annotation.PrepareValuesProducer;
import org.bober.calculation.core.annotation.ValuesProducerResult;
import org.bober.calculation.core.interfaces.CalculationFlow;
import org.bober.calculation.core.interfaces.ProducerResult;
import org.bober.calculation.core.interfaces.ValuesProducer;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.bober.calculation.core.interfaces.ValuesProducer.RESULT;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class OnDtoClassProducers {

    public static final Integer TEST_VALUE = 42;

    private CalculationFlow flow = new CalculationFlowImpl();

    @Test
    public void test_ComplexProducer() throws Exception {
        TestDto dto = flow.produceDto(TestDto.class);

        assertThat(dto, notNullValue());
        assertThat(dto.producerResult.get(), is(TEST_VALUE));
    }



    @PrepareValuesProducer(ValueSourceImpl.class)
    public static class TestDto {
        @ValuesProducerResult(producer = Producer.class, resultName = RESULT)
        public ProducerResult<Integer> producerResult;
    }


    public static class Producer implements ValuesProducer {
        @ValuesProducerResult(producer = ValueSource.class, resultName = RESULT)
        private ProducerResult<Integer> valueSource;

        @Override
        public Map<String, Object> getResult() {
            return Collections.singletonMap(RESULT, (Object) valueSource.get());
        }
    }


    public static class ValueSourceImpl implements ValueSource {
        @Override
        public Map<String, Object> getResult() {
            return Collections.singletonMap(RESULT, (Object) TEST_VALUE);
        }
    }

    public interface ValueSource extends ValuesProducer {
    }

}