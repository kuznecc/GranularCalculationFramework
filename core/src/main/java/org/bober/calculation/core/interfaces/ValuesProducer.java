package org.bober.calculation.core.interfaces;

import java.util.Map;

/**
 * producer with obtain all dependencies and produce a resultName map.
 */
public interface ValuesProducer {
    String RESULT = "result";

    Map<String, Object> getResult();

}
