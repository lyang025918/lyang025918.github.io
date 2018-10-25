// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CollationKeyFilterFactory.java

package org.apache.lucene.collation;

import java.io.*;
import java.text.*;
import java.util.Locale;
import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.collation:
//			CollationKeyFilter

/**
 * @deprecated Class CollationKeyFilterFactory is deprecated
 */

public class CollationKeyFilterFactory extends TokenFilterFactory
	implements MultiTermAwareComponent, ResourceLoaderAware
{

	private Collator collator;

	public CollationKeyFilterFactory()
	{
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		String custom = (String)args.get("custom");
		String language = (String)args.get("language");
		String country = (String)args.get("country");
		String variant = (String)args.get("variant");
		String strength = (String)args.get("strength");
		String decomposition = (String)args.get("decomposition");
		if (custom == null && language == null)
			throw new IllegalArgumentException("Either custom or language is required.");
		if (custom != null && (language != null || country != null || variant != null))
			throw new IllegalArgumentException("Cannot specify both language and custom. To tailor rules for a built-in language, see the javadocs for RuleBasedCollator. Then save the entire customized ruleset to a file, and use with the custom parameter");
		if (language != null)
			collator = createFromLocale(language, country, variant);
		else
			collator = createFromRules(custom, loader);
		if (strength != null)
			if (strength.equalsIgnoreCase("primary"))
				collator.setStrength(0);
			else
			if (strength.equalsIgnoreCase("secondary"))
				collator.setStrength(1);
			else
			if (strength.equalsIgnoreCase("tertiary"))
				collator.setStrength(2);
			else
			if (strength.equalsIgnoreCase("identical"))
				collator.setStrength(3);
			else
				throw new IllegalArgumentException((new StringBuilder()).append("Invalid strength: ").append(strength).toString());
		if (decomposition != null)
			if (decomposition.equalsIgnoreCase("no"))
				collator.setDecomposition(0);
			else
			if (decomposition.equalsIgnoreCase("canonical"))
				collator.setDecomposition(1);
			else
			if (decomposition.equalsIgnoreCase("full"))
				collator.setDecomposition(2);
			else
				throw new IllegalArgumentException((new StringBuilder()).append("Invalid decomposition: ").append(decomposition).toString());
	}

	public TokenStream create(TokenStream input)
	{
		return new CollationKeyFilter(input, collator);
	}

	private Collator createFromLocale(String language, String country, String variant)
	{
		if (language != null && country == null && variant != null)
			throw new IllegalArgumentException("To specify variant, country is required");
		Locale locale;
		if (language != null && country != null && variant != null)
			locale = new Locale(language, country, variant);
		else
		if (language != null && country != null)
			locale = new Locale(language, country);
		else
			locale = new Locale(language);
		return Collator.getInstance(locale);
	}

	private Collator createFromRules(String fileName, ResourceLoader loader)
		throws IOException
	{
		Exception exception;
		InputStream input = null;
		RuleBasedCollator rulebasedcollator;
		try
		{
			input = loader.openResource(fileName);
			String rules = toUTF8String(input);
			rulebasedcollator = new RuleBasedCollator(rules);
		}
		catch (ParseException e)
		{
			throw new IOException("ParseException thrown while parsing rules", e);
		}
		finally
		{
			IOUtils.closeWhileHandlingException(new Closeable[] {
				input
			});
		}
		IOUtils.closeWhileHandlingException(new Closeable[] {
			input
		});
		return rulebasedcollator;
		throw exception;
	}

	public AbstractAnalysisFactory getMultiTermComponent()
	{
		return this;
	}

	private String toUTF8String(InputStream in)
		throws IOException
	{
		StringBuilder sb = new StringBuilder();
		char buffer[] = new char[1024];
		Reader r = IOUtils.getDecodingReader(in, IOUtils.CHARSET_UTF_8);
		for (int len = 0; (len = r.read(buffer)) > 0;)
			sb.append(buffer, 0, len);

		return sb.toString();
	}
}
