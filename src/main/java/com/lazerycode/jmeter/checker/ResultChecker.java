/**
 *
 */
package com.lazerycode.jmeter.checker;

import static com.lazerycode.jmeter.analyzer.config.Environment.ENVIRONMENT;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.maven.plugin.MojoFailureException;

import com.lazerycode.jmeter.analyzer.Check;
import com.lazerycode.jmeter.analyzer.CheckResult;
import com.lazerycode.jmeter.analyzer.RequestGroup;
import com.lazerycode.jmeter.analyzer.parser.AggregatedResponses;

/**
 *
 */
public class ResultChecker {

    public void check(Map<String, AggregatedResponses> jmeterResults) throws MojoFailureException {
        boolean check = true;

        for (String key : jmeterResults.keySet()) {
            AggregatedResponses aggregatedResponses = jmeterResults.get(key);
            CheckResult checkResult = getCheckResult(key);

            // Normalize our throughput scale and use it to set our getSuccesses method

            String scale = "";
            if (ENVIRONMENT.getThroughputScale().toLowerCase().equals("minutes") ||
                    ENVIRONMENT.getThroughputScale().toLowerCase().equals("mins"))
                ENVIRONMENT.setThroughputScale("minutes");
            else
                ENVIRONMENT.setThroughputScale("seconds");

            // Check throughput
            check &= checkValue(checkResult.getThroughput(),
                  ENVIRONMENT.getThroughputScale().toLowerCase().equals("minutes") ?
                          aggregatedResponses.getDuration().getSuccessPerMinute() :
                          aggregatedResponses.getDuration().getSuccessPerSecond(), key, "throughput");

            // Check errors
            double percentErrors = (((double) aggregatedResponses.getDuration().getErrorsCount()) /
                    (aggregatedResponses.getDuration().getErrorsCount() + aggregatedResponses.getDuration().getSuccessCount())) * 100;
            check &= checkValue(checkResult.getErrors(), percentErrors,
                    key, "errors");

            // Check Avg Response Time
            double avgResponseTime = (((double) aggregatedResponses.getDuration().getTotal() /
                    (aggregatedResponses.getDuration().getSuccessCount())));
            avgResponseTime = new BigDecimal(avgResponseTime).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            check &= checkValue(checkResult.getResponseTimeAvg(), avgResponseTime, key, "responseTimeAvg");
        }

        if (!check) {
            throw new MojoFailureException("Check is incorrect.");
        }
    }

    private CheckResult getCheckResult(String key) {
        CheckResult result = null;

        // Check by request group
        if (null != ENVIRONMENT.getRequestGroups()) {
            for (RequestGroup requestGroup : ENVIRONMENT.getRequestGroups()) {
                if (key.equals(requestGroup.getName())) {
                    result = requestGroup.getCheckResult();
                    break;
                }
            }
        }

        // Check by default
        if (null == result) {
            result = ENVIRONMENT.getCheckResult();
        }

        return result;
    }

    private boolean checkValue(Check check, double value, String key, String valueDescription) {
        Boolean valid = check.valid(value);
        if (null == valid) {
            valid = true;
            ENVIRONMENT.getLog().info(new StringBuilder(key)
                    .append(" : Check ").append(valueDescription)
                    .append(" disabling : ").append(value).toString());
        } else if (!valid) {
            ENVIRONMENT.getLog().error(new StringBuilder(key)
                    .append(" : Check ").append(valueDescription)
                    .append(" is incorrect : ").append(value)
                    .append(" (minValue : ").append(check.getMinValue())
                    .append(", maxValue : ").append(check.getMaxValue())
                    .append(")").toString());
        } else {
            ENVIRONMENT.getLog().info(new StringBuilder(key)
                    .append(" : Check ").append(valueDescription)
                    .append(" is correct : ").append(value)
                    .append(" (minValue : ").append(check.getMinValue())
                    .append(", maxValue : ").append(check.getMaxValue())
                    .append(")").toString());
        }
        return valid;
    }

}
