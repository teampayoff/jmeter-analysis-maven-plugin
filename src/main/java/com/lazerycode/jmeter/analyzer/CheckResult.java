/**
 *
 */
package com.lazerycode.jmeter.analyzer;

/**
 *
 */
public class CheckResult {

    // Default check is disabling
    private Check throughput = new Check();

    private Check responseTimeAvg = new Check();

    // Default check is disabling
    private Check errors = new Check();

    private Check throughputScale = new Check();

    public Check getThroughput() {
        return throughput;
    }

    public void setThroughput(Check pThroughput) {
        throughput = pThroughput;
    }

    public Check getErrors() {
        return errors;
    }

    public void setErrors(Check pErrors) {
        errors = pErrors;
    }

    public Check getResponseTimeAvg() { return responseTimeAvg; }

    public void setResponseTimeAvg(Check pResponseTimeAvg) { responseTimeAvg = pResponseTimeAvg; }

    public Check getThroughputScale() { return throughputScale; }

    public void setThroughputScale(Check pThroughputScale) { throughputScale = pThroughputScale; }


}
