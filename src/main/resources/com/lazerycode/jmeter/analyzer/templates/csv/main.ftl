<#ftl/>
<#-- @ftlvariable name="scale" type="java.util.String" -->
<#-- @ftlvariable name="self" type="java.util.Map<java.lang.String, com.lazerycode.jmeter.analyzer.statistics.Samples>" -->

<#list self?keys as key>
  <#assign samples=self(key)/>
  <#assign throughputScale=samples.throughputScale/>
</#list>

<#if throughputScale == "minutes">
    <#assign scale = "perminute">
<#else>
    <#assign scale = "persecond">
</#if>

uri;count;total;min;average;max;standarddeviation;${scale};success;errors
<#list self?keys as key>
  <#assign samples=self(key)/>
  <#include "samples.ftl" />
</#list>