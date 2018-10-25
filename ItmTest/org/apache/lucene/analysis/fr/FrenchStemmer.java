// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FrenchStemmer.java

package org.apache.lucene.analysis.fr;

import java.util.Locale;

/**
 * @deprecated Class FrenchStemmer is deprecated
 */

public class FrenchStemmer
{

	private static final Locale locale = new Locale("fr", "FR");
	private StringBuilder sb;
	private StringBuilder tb;
	private String R0;
	private String RV;
	private String R1;
	private String R2;
	private boolean suite;
	private boolean modified;

	public FrenchStemmer()
	{
		sb = new StringBuilder();
		tb = new StringBuilder();
	}

	protected String stem(String term)
	{
		if (!isStemmable(term))
			return term;
		term = term.toLowerCase(locale);
		sb.delete(0, sb.length());
		sb.insert(0, term);
		modified = false;
		suite = false;
		sb = treatVowels(sb);
		setStrings();
		step1();
		if ((!modified || suite) && RV != null)
		{
			suite = step2a();
			if (!suite)
				step2b();
		}
		if (modified || suite)
			step3();
		else
			step4();
		step5();
		step6();
		return sb.toString();
	}

	private void setStrings()
	{
		R0 = sb.toString();
		RV = retrieveRV(sb);
		R1 = retrieveR(sb);
		if (R1 != null)
		{
			tb.delete(0, tb.length());
			tb.insert(0, R1);
			R2 = retrieveR(tb);
		} else
		{
			R2 = null;
		}
	}

	private void step1()
	{
		String suffix[] = {
			"ances", "iqUes", "ismes", "ables", "istes", "ance", "iqUe", "isme", "able", "iste"
		};
		deleteFrom(R2, suffix);
		replaceFrom(R2, new String[] {
			"logies", "logie"
		}, "log");
		replaceFrom(R2, new String[] {
			"usions", "utions", "usion", "ution"
		}, "u");
		replaceFrom(R2, new String[] {
			"ences", "ence"
		}, "ent");
		String search[] = {
			"atrices", "ateurs", "ations", "atrice", "ateur", "ation"
		};
		deleteButSuffixFromElseReplace(R2, search, "ic", true, R0, "iqU");
		deleteButSuffixFromElseReplace(R2, new String[] {
			"ements", "ement"
		}, "eus", false, R0, "eux");
		deleteButSuffixFrom(R2, new String[] {
			"ements", "ement"
		}, "ativ", false);
		deleteButSuffixFrom(R2, new String[] {
			"ements", "ement"
		}, "iv", false);
		deleteButSuffixFrom(R2, new String[] {
			"ements", "ement"
		}, "abl", false);
		deleteButSuffixFrom(R2, new String[] {
			"ements", "ement"
		}, "iqU", false);
		deleteFromIfTestVowelBeforeIn(R1, new String[] {
			"issements", "issement"
		}, false, R0);
		deleteFrom(RV, new String[] {
			"ements", "ement"
		});
		deleteButSuffixFromElseReplace(R2, new String[] {
			"it\351s", "it\351"
		}, "abil", false, R0, "abl");
		deleteButSuffixFromElseReplace(R2, new String[] {
			"it\351s", "it\351"
		}, "ic", false, R0, "iqU");
		deleteButSuffixFrom(R2, new String[] {
			"it\351s", "it\351"
		}, "iv", true);
		String autre[] = {
			"ifs", "ives", "if", "ive"
		};
		deleteButSuffixFromElseReplace(R2, autre, "icat", false, R0, "iqU");
		deleteButSuffixFromElseReplace(R2, autre, "at", true, R2, "iqU");
		replaceFrom(R0, new String[] {
			"eaux"
		}, "eau");
		replaceFrom(R1, new String[] {
			"aux"
		}, "al");
		deleteButSuffixFromElseReplace(R2, new String[] {
			"euses", "euse"
		}, "", true, R1, "eux");
		deleteFrom(R2, new String[] {
			"eux"
		});
		boolean temp = false;
		temp = replaceFrom(RV, new String[] {
			"amment"
		}, "ant");
		if (temp)
			suite = true;
		temp = replaceFrom(RV, new String[] {
			"emment"
		}, "ent");
		if (temp)
			suite = true;
		temp = deleteFromIfTestVowelBeforeIn(RV, new String[] {
			"ments", "ment"
		}, true, RV);
		if (temp)
			suite = true;
	}

	private boolean step2a()
	{
		String search[] = {
			"\356mes", "\356tes", "iraIent", "irait", "irais", "irai", "iras", "ira", "irent", "iriez", 
			"irez", "irions", "irons", "iront", "issaIent", "issais", "issantes", "issante", "issants", "issant", 
			"issait", "issais", "issions", "issons", "issiez", "issez", "issent", "isses", "isse", "ir", 
			"is", "\356t", "it", "ies", "ie", "i"
		};
		return deleteFromIfTestVowelBeforeIn(RV, search, false, RV);
	}

	private void step2b()
	{
		String suffix[] = {
			"eraIent", "erais", "erait", "erai", "eras", "erions", "eriez", "erons", "eront", "erez", 
			"\350rent", "era", "\351es", "iez", "\351e", "\351s", "er", "ez", "\351"
		};
		deleteFrom(RV, suffix);
		String search[] = {
			"assions", "assiez", "assent", "asses", "asse", "aIent", "antes", "aIent", "Aient", "ante", 
			"\342mes", "\342tes", "ants", "ant", "ait", "a\356t", "ais", "Ait", "A\356t", "Ais", 
			"\342t", "as", "ai", "Ai", "a"
		};
		deleteButSuffixFrom(RV, search, "e", true);
		deleteFrom(R2, new String[] {
			"ions"
		});
	}

	private void step3()
	{
		if (sb.length() > 0)
		{
			char ch = sb.charAt(sb.length() - 1);
			if (ch == 'Y')
			{
				sb.setCharAt(sb.length() - 1, 'i');
				setStrings();
			} else
			if (ch == '\347')
			{
				sb.setCharAt(sb.length() - 1, 'c');
				setStrings();
			}
		}
	}

	private void step4()
	{
		if (sb.length() > 1)
		{
			char ch = sb.charAt(sb.length() - 1);
			if (ch == 's')
			{
				char b = sb.charAt(sb.length() - 2);
				if (b != 'a' && b != 'i' && b != 'o' && b != 'u' && b != '\350' && b != 's')
				{
					sb.delete(sb.length() - 1, sb.length());
					setStrings();
				}
			}
		}
		boolean found = deleteFromIfPrecededIn(R2, new String[] {
			"ion"
		}, RV, "s");
		if (!found)
			found = deleteFromIfPrecededIn(R2, new String[] {
				"ion"
			}, RV, "t");
		replaceFrom(RV, new String[] {
			"I\350re", "i\350re", "Ier", "ier"
		}, "i");
		deleteFrom(RV, new String[] {
			"e"
		});
		deleteFromIfPrecededIn(RV, new String[] {
			"\353"
		}, R0, "gu");
	}

	private void step5()
	{
		if (R0 != null && (R0.endsWith("enn") || R0.endsWith("onn") || R0.endsWith("ett") || R0.endsWith("ell") || R0.endsWith("eill")))
		{
			sb.delete(sb.length() - 1, sb.length());
			setStrings();
		}
	}

	private void step6()
	{
		if (R0 != null && R0.length() > 0)
		{
			boolean seenVowel = false;
			boolean seenConson = false;
			int pos = -1;
			for (int i = R0.length() - 1; i > -1; i--)
			{
				char ch = R0.charAt(i);
				if (isVowel(ch))
				{
					if (!seenVowel && (ch == '\351' || ch == '\350'))
					{
						pos = i;
						break;
					}
					seenVowel = true;
					continue;
				}
				if (seenVowel)
					break;
				seenConson = true;
			}

			if (pos > -1 && seenConson && !seenVowel)
				sb.setCharAt(pos, 'e');
		}
	}

	private boolean deleteFromIfPrecededIn(String source, String search[], String from, String prefix)
	{
		boolean found = false;
		if (source != null)
		{
			int i = 0;
			do
			{
				if (i >= search.length)
					break;
				if (source.endsWith(search[i]) && from != null && from.endsWith((new StringBuilder()).append(prefix).append(search[i]).toString()))
				{
					sb.delete(sb.length() - search[i].length(), sb.length());
					found = true;
					setStrings();
					break;
				}
				i++;
			} while (true);
		}
		return found;
	}

	private boolean deleteFromIfTestVowelBeforeIn(String source, String search[], boolean vowel, String from)
	{
		boolean found = false;
		if (source != null && from != null)
		{
			for (int i = 0; i < search.length; i++)
			{
				if (!source.endsWith(search[i]) || search[i].length() + 1 > from.length())
					continue;
				boolean test = isVowel(sb.charAt(sb.length() - (search[i].length() + 1)));
				if (test != vowel)
					continue;
				sb.delete(sb.length() - search[i].length(), sb.length());
				modified = true;
				found = true;
				setStrings();
				break;
			}

		}
		return found;
	}

	private void deleteButSuffixFrom(String source, String search[], String prefix, boolean without)
	{
		if (source != null)
		{
			int i = 0;
			do
			{
				if (i >= search.length)
					break;
				if (source.endsWith((new StringBuilder()).append(prefix).append(search[i]).toString()))
				{
					sb.delete(sb.length() - (prefix.length() + search[i].length()), sb.length());
					modified = true;
					setStrings();
					break;
				}
				if (without && source.endsWith(search[i]))
				{
					sb.delete(sb.length() - search[i].length(), sb.length());
					modified = true;
					setStrings();
					break;
				}
				i++;
			} while (true);
		}
	}

	private void deleteButSuffixFromElseReplace(String source, String search[], String prefix, boolean without, String from, String replace)
	{
		if (source != null)
		{
			int i = 0;
			do
			{
				if (i >= search.length)
					break;
				if (source.endsWith((new StringBuilder()).append(prefix).append(search[i]).toString()))
				{
					sb.delete(sb.length() - (prefix.length() + search[i].length()), sb.length());
					modified = true;
					setStrings();
					break;
				}
				if (from != null && from.endsWith((new StringBuilder()).append(prefix).append(search[i]).toString()))
				{
					sb.replace(sb.length() - (prefix.length() + search[i].length()), sb.length(), replace);
					modified = true;
					setStrings();
					break;
				}
				if (without && source.endsWith(search[i]))
				{
					sb.delete(sb.length() - search[i].length(), sb.length());
					modified = true;
					setStrings();
					break;
				}
				i++;
			} while (true);
		}
	}

	private boolean replaceFrom(String source, String search[], String replace)
	{
		boolean found = false;
		if (source != null)
		{
			int i = 0;
			do
			{
				if (i >= search.length)
					break;
				if (source.endsWith(search[i]))
				{
					sb.replace(sb.length() - search[i].length(), sb.length(), replace);
					modified = true;
					found = true;
					setStrings();
					break;
				}
				i++;
			} while (true);
		}
		return found;
	}

	private void deleteFrom(String source, String suffix[])
	{
		if (source != null)
		{
			int i = 0;
			do
			{
				if (i >= suffix.length)
					break;
				if (source.endsWith(suffix[i]))
				{
					sb.delete(sb.length() - suffix[i].length(), sb.length());
					modified = true;
					setStrings();
					break;
				}
				i++;
			} while (true);
		}
	}

	private boolean isVowel(char ch)
	{
		switch (ch)
		{
		case 97: // 'a'
		case 101: // 'e'
		case 105: // 'i'
		case 111: // 'o'
		case 117: // 'u'
		case 121: // 'y'
		case 224: 
		case 226: 
		case 232: 
		case 233: 
		case 234: 
		case 235: 
		case 238: 
		case 239: 
		case 244: 
		case 249: 
		case 251: 
		case 252: 
			return true;
		}
		return false;
	}

	private String retrieveR(StringBuilder buffer)
	{
		int len = buffer.length();
		int pos = -1;
		int c = 0;
		do
		{
			if (c >= len)
				break;
			if (isVowel(buffer.charAt(c)))
			{
				pos = c;
				break;
			}
			c++;
		} while (true);
		if (pos > -1)
		{
			int consonne = -1;
			int c = pos;
			do
			{
				if (c >= len)
					break;
				if (!isVowel(buffer.charAt(c)))
				{
					consonne = c;
					break;
				}
				c++;
			} while (true);
			if (consonne > -1 && consonne + 1 < len)
				return buffer.substring(consonne + 1, len);
			else
				return null;
		} else
		{
			return null;
		}
	}

	private String retrieveRV(StringBuilder buffer)
	{
		int len = buffer.length();
		if (buffer.length() > 3)
		{
			if (isVowel(buffer.charAt(0)) && isVowel(buffer.charAt(1)))
				return buffer.substring(3, len);
			int pos = 0;
			int c = 1;
			do
			{
				if (c >= len)
					break;
				if (isVowel(buffer.charAt(c)))
				{
					pos = c;
					break;
				}
				c++;
			} while (true);
			if (pos + 1 < len)
				return buffer.substring(pos + 1, len);
			else
				return null;
		} else
		{
			return null;
		}
	}

	private StringBuilder treatVowels(StringBuilder buffer)
	{
		for (int c = 0; c < buffer.length(); c++)
		{
			char ch = buffer.charAt(c);
			if (c == 0)
			{
				if (buffer.length() > 1 && ch == 'y' && isVowel(buffer.charAt(c + 1)))
					buffer.setCharAt(c, 'Y');
				continue;
			}
			if (c == buffer.length() - 1)
			{
				if (ch == 'u' && buffer.charAt(c - 1) == 'q')
					buffer.setCharAt(c, 'U');
				if (ch == 'y' && isVowel(buffer.charAt(c - 1)))
					buffer.setCharAt(c, 'Y');
				continue;
			}
			if (ch == 'u')
				if (buffer.charAt(c - 1) == 'q')
					buffer.setCharAt(c, 'U');
				else
				if (isVowel(buffer.charAt(c - 1)) && isVowel(buffer.charAt(c + 1)))
					buffer.setCharAt(c, 'U');
			if (ch == 'i' && isVowel(buffer.charAt(c - 1)) && isVowel(buffer.charAt(c + 1)))
				buffer.setCharAt(c, 'I');
			if (ch == 'y' && (isVowel(buffer.charAt(c - 1)) || isVowel(buffer.charAt(c + 1))))
				buffer.setCharAt(c, 'Y');
		}

		return buffer;
	}

	private boolean isStemmable(String term)
	{
		boolean upper = false;
		int first = -1;
		for (int c = 0; c < term.length(); c++)
		{
			if (!Character.isLetter(term.charAt(c)))
				return false;
			if (!Character.isUpperCase(term.charAt(c)))
				continue;
			if (upper)
				return false;
			first = c;
			upper = true;
		}

		return first <= 0;
	}

}
