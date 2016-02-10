<#ftl/>
<#-- @ftlvariable name="key" type="java.lang.String" -->
<#-- @ftlvariable name="samples" type="com.lazerycode.jmeter.analyzer.statistics.Samples" -->
<#-- @ftlvariable name="samplesSuccess" type="java.lang.Long" -->
<#if samples.throughputScale == "minutes">
    <#assign samplesSuccess = samples.successPerMinute>
<#else>
    <#assign samplesSuccess = samples.successPerSecond>
</#if>
<td>"${key}"</td><td>${samples.successCount + samples.errorsCount}</td><td>${samples.total}</td><td>${samples.min}</td><td>${samples.average}</td><td>${samples.max}</td><td>${samples.standardDeviation}</td><td>${samplesSuccess}</td><td>${samples.successCount}</td><td>${samples.errorsCount}</td>
