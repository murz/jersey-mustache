package com.hazardousholdings.jersey.mustache.guice;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.github.mustachejava.Mustache;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.google.inject.Provider;

public class ClientsideTemplatesProvider implements Provider<String> {
	
	private final String htmlBlob;
	private Function<String, String> preprocessor;
	
	//TODO: get this externally;
	private final String mustacheJs = "var Mustache=\"undefined\"!==typeof module&&module.exports||{};" +
"(function(j){function G(a){return(\"\"+a).replace(/&(?!\\w+;)|[<>\"']/g,function(a){return H[a]||a})}function t(a,c,d,e){for(var e=e||\"<template>\",b=c.split(\"\\n\"),f=Math.max(d-3,0),g=Math.min(b.length,d+3),b=b.slice(f,g),i=0,l=b.length;i<l;++i)g=i+f+1,b[i]=(g===d?\" >> \":\"    \")+b[i];a.template=c;a.line=d;a.file=e;a.message=[e+\":\"+d,b.join(\"\\n\"),\"\",a.message].join(\"\\n\");return a}function u(a,c,d){if(\".\"===a)return c[c.length-1];for(var a=a.split(\".\"),e=a.length-1,b=a[e],f,g,i=c.length,l,j;i;){j=c.slice(0);" +
"g=c[--i];for(l=0;l<e;){g=g[a[l++]];if(null==g)break;j.push(g)}if(g&&\"object\"===typeof g&&b in g){f=g[b];break}}\"function\"===typeof f&&(f=f.call(j[j.length-1]));return null==f?d:f}function I(a,c,d,e){var b=\"\",a=u(a,c);if(e){if(null==a||!1===a||q(a)&&0===a.length)b+=d()}else if(q(a))y(a,function(a){c.push(a);b+=d();c.pop()});else if(\"object\"===typeof a)c.push(a),b+=d(),c.pop();else if(\"function\"===typeof a)var f=c[c.length-1],b=b+(a.call(f,d(),function(a){return r(a,f)})||\"\");else a&&(b+=d());return b}" +
"function z(a,c){for(var c=c||{},d=c.tags||j.tags,e=d[0],b=d[d.length-1],f=['var buffer = \"\";',\"\\nvar line = 1;\",\"\\ntry {\",'\\nbuffer += \"'],g=[],i=!1,l=!1,r=function(){if(i&&!l&&!c.space)for(;g.length;)f.splice(g.pop(),1);else g=[];l=i=!1},n=[],v,p,q,w=function(a){d=o(a).split(/\\s+/);p=d[0];q=d[d.length-1]},x=function(a){f.push('\";',v,'\\nvar partial = partials[\"'+o(a)+'\"];',\"\\nif (partial) {\",\"\\n  buffer += render(partial,stack[stack.length - 1],partials);\",\"\\n}\",'\\nbuffer += \"')},u=function(b,d){var e=" +
"o(b);if(\"\"===e)throw t(Error(\"Section name may not be empty\"),a,s,c.file);n.push({name:e,inverted:d});f.push('\";',v,'\\nvar name = \"'+e+'\";',\"\\nvar callback = (function () {\",\"\\n  return function () {\",'\\n    var buffer = \"\";','\\nbuffer += \"')},y=function(a){u(a,!0)},z=function(b){var b=o(b),d=0!=n.length&&n[n.length-1].name;if(!d||b!=d)throw t(Error('Section named \"'+b+'\" was never opened'),a,s,c.file);b=n.pop();f.push('\";',\"\\n    return buffer;\",\"\\n  };\",\"\\n})();\");b.inverted?f.push(\"\\nbuffer += renderSection(name,stack,callback,true);\"):" +
"f.push(\"\\nbuffer += renderSection(name,stack,callback);\");f.push('\\nbuffer += \"')},A=function(a){f.push('\";',v,'\\nbuffer += lookup(\"'+o(a)+'\",stack,\"\");','\\nbuffer += \"')},B=function(a){f.push('\";',v,'\\nbuffer += escapeHTML(lookup(\"'+o(a)+'\",stack,\"\"));','\\nbuffer += \"')},s=1,m,k,h=0,C=a.length;h<C;++h)if(a.slice(h,h+e.length)===e){h+=e.length;m=a.substr(h,1);v=\"\\nline = \"+s+\";\";p=e;q=b;i=!0;switch(m){case \"!\":h++;k=null;break;case \"=\":h++;b=\"=\"+b;k=w;break;case \">\":h++;k=x;break;case \"#\":h++;k=u;" +
"break;case \"^\":h++;k=y;break;case \"/\":h++;k=z;break;case \"{\":b=\"}\"+b;case \"&\":h++;l=!0;k=A;break;default:l=!0,k=B}m=a.indexOf(b,h);if(-1===m)throw t(Error('Tag \"'+e+'\" was not closed properly'),a,s,c.file);e=a.substring(h,m);k&&k(e);for(k=0;~(k=e.indexOf(\"\\n\",k));)s++,k++;h=m+b.length-1;e=p;b=q}else switch(m=a.substr(h,1),m){case '\"':case \"\\\\\":l=!0;f.push(\"\\\\\"+m);break;case \"\\r\":break;case \"\\n\":g.push(f.length);f.push(\"\\\\n\");r();s++;break;default:D.test(m)?g.push(f.length):l=!0,f.push(m)}if(0!=n.length)throw t(Error('Section \"'+" +
"n[n.length-1].name+'\" was not closed properly'),a,s,c.file);r();f.push('\";',\"\\nreturn buffer;\",\"\\n} catch (e) { throw {error: e, line: line}; }\");b=f.join(\"\").replace(/buffer \\+= \"\";\\n/g,\"\");c.debug&&(\"undefined\"!=typeof console&&console.log?console.log(b):\"function\"===typeof print&&print(b));return b}function A(a,c){var d=z(a,c),e=new Function(\"view,partials,stack,lookup,escapeHTML,renderSection,render\",d);return function(b,d){var d=d||{},g=[b];try{return e(b,d,g,u,G,I,r)}catch(i){throw t(i.error," +
"a,i.line,c.file);}}}function B(a,c){c=c||{};return!1!==c.cache?(p[a]||(p[a]=A(a,c)),p[a]):A(a,c)}function r(a,c,d){return B(a)(c,d)}j.name=\"mustache.js\";j.version=\"0.5.0-dev\";j.tags=[\"{{\",\"}}\"];j.parse=z;j.compile=B;j.render=r;j.clearCache=function(){p={}};j.to_html=function(a,c,d,e){a=r(a,c,d);if(\"function\"===typeof e)e(a);else return a};var J=Object.prototype.toString,C=Array.isArray,E=Array.prototype.forEach,F=String.prototype.trim,q;q=C?C:function(a){return\"[object Array]\"===J.call(a)};var y;" +
"y=E?function(a,c,d){return E.call(a,c,d)}:function(a,c,d){for(var e=0,b=a.length;e<b;++e)c.call(d,a[e],e,a)};var D=/^\\s*$/,o;if(F)o=function(a){return null==a?\"\":F.call(a)};else{var w,x;D.test(\"\\u00a0\")?(w=/^\\s+/,x=/\\s+$/):(w=/^[\\s\\xA0]+/,x=/[\\s\\xA0]+$/);o=function(a){return a==null?\"\":(\"\"+a).replace(w,\"\").replace(x,\"\")}}var H={\"&\":\"&amp;\",\"<\":\"&lt;\",\">\":\"&gt;\",'\"':\"&quot;\",\"'\":\"&#39;\"},p={}})(Mustache);";
	
	public ClientsideTemplatesProvider(String basePath, List<String> dirs, Function<String, String> preprocessor) {
		this.preprocessor = preprocessor;
		StringBuilder sb = new StringBuilder();
		sb.append("<script type='text/javascript'>");
		sb.append("var mustache = {};");
		sb.append("(function(m) { ");
		sb.append(mustacheJs);
		sb.append("var t = {};");
		for (String s : dirs) {
			sb.append(buildHtmlBlob(new File(basePath + '/' + s), ""));
		}
		sb.append("m.render = function(x, y) { return Mustache.render(t[x].template, y, t[x].partials); }; ");
		sb.append("m.setTemplate = function(x, y) { t[x] = {template: y, partials: null}; }; ");
		sb.append("})(mustache);");
		sb.append("</script>");
		this.htmlBlob = sb.toString();
	}
	
	private String buildHtmlBlob(File dir, String namespace) {
		if (!namespace.isEmpty()) {
			namespace += '.';
		}
		if (dir.isDirectory()) {
    		namespace += dir.getName();
    		StringBuilder sb = new StringBuilder();
    		for (File f : dir.listFiles()) {
    			 sb.append(buildHtmlBlob(f, namespace));
    		}
    		return sb.toString();
    	} else if (dir.exists()) {
    		try {
    			String key = namespace + dir.getName().replace(".html", "");
    			String template = Joiner.on(' ').join(Files.readLines(dir, Charsets.UTF_8));
    			if (preprocessor != null) {
    				template = preprocessor.apply(template);
    			}
    			return "t['" + key + "'] = {template: '" + template + "', partials: null}; ";
    		} catch (IOException e) {
    			throw new RuntimeException(e);
    		}
    	} else {
    		return "";
    	}
	}

	public String get() {
		return htmlBlob;
	}

}
