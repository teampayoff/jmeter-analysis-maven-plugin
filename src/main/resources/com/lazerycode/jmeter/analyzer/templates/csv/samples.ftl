<#ftl/>
<#-- @ftlvariable name="key" type="java.lang.String" -->
<#-- @ftlvariable name="samplesSuccess" type="java.lang.Long" -->
<#if samples.throughputScale == "minutes">
    <#assign samplesSuccess = samples.successPerMinute>
<#else>
    <#assign samplesSuccess = samples.successPerSecond>
</#if>

"${key}";${samples.successCount + samples.errorsCount};${samples.total};${samples.min};${samples.average};${samples.max};${samples.standardDeviation};${samplesSuccess};${samples.successCount};${samples.errorsCount}
