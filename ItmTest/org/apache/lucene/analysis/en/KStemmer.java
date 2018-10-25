// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KStemmer.java

package org.apache.lucene.analysis.en;

import org.apache.lucene.analysis.util.CharArrayMap;
import org.apache.lucene.analysis.util.OpenStringBuilder;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.en:
//			KStemData1, KStemData2, KStemData3, KStemData4, 
//			KStemData5, KStemData6, KStemData7, KStemData8

public class KStemmer
{
	static class DictEntry
	{

		boolean exception;
		String root;

		DictEntry(String root, boolean isException)
		{
			this.root = root;
			exception = isException;
		}
	}


	private static final int MaxWordLen = 50;
	private static final String exceptionWords[] = {
		"aide", "bathe", "caste", "cute", "dame", "dime", "doge", "done", "dune", "envelope", 
		"gage", "grille", "grippe", "lobe", "mane", "mare", "nape", "node", "pane", "pate", 
		"plane", "pope", "programme", "quite", "ripe", "rote", "rune", "sage", "severe", "shoppe", 
		"sine", "slime", "snipe", "steppe", "suite", "swinge", "tare", "tine", "tope", "tripe", 
		"twine"
	};
	private static final String directConflations[][] = {
		{
			"aging", "age"
		}, {
			"going", "go"
		}, {
			"goes", "go"
		}, {
			"lying", "lie"
		}, {
			"using", "use"
		}, {
			"owing", "owe"
		}, {
			"suing", "sue"
		}, {
			"dying", "die"
		}, {
			"tying", "tie"
		}, {
			"vying", "vie"
		}, {
			"aged", "age"
		}, {
			"used", "use"
		}, {
			"vied", "vie"
		}, {
			"cued", "cue"
		}, {
			"died", "die"
		}, {
			"eyed", "eye"
		}, {
			"hued", "hue"
		}, {
			"iced", "ice"
		}, {
			"lied", "lie"
		}, {
			"owed", "owe"
		}, {
			"sued", "sue"
		}, {
			"toed", "toe"
		}, {
			"tied", "tie"
		}, {
			"does", "do"
		}, {
			"doing", "do"
		}, {
			"aeronautical", "aeronautics"
		}, {
			"mathematical", "mathematics"
		}, {
			"political", "politics"
		}, {
			"metaphysical", "metaphysics"
		}, {
			"cylindrical", "cylinder"
		}, {
			"nazism", "nazi"
		}, {
			"ambiguity", "ambiguous"
		}, {
			"barbarity", "barbarous"
		}, {
			"credulity", "credulous"
		}, {
			"generosity", "generous"
		}, {
			"spontaneity", "spontaneous"
		}, {
			"unanimity", "unanimous"
		}, {
			"voracity", "voracious"
		}, {
			"fled", "flee"
		}, {
			"miscarriage", "miscarry"
		}
	};
	private static final String countryNationality[][] = {
		{
			"afghan", "afghanistan"
		}, {
			"african", "africa"
		}, {
			"albanian", "albania"
		}, {
			"algerian", "algeria"
		}, {
			"american", "america"
		}, {
			"andorran", "andorra"
		}, {
			"angolan", "angola"
		}, {
			"arabian", "arabia"
		}, {
			"argentine", "argentina"
		}, {
			"armenian", "armenia"
		}, {
			"asian", "asia"
		}, {
			"australian", "australia"
		}, {
			"austrian", "austria"
		}, {
			"azerbaijani", "azerbaijan"
		}, {
			"azeri", "azerbaijan"
		}, {
			"bangladeshi", "bangladesh"
		}, {
			"belgian", "belgium"
		}, {
			"bermudan", "bermuda"
		}, {
			"bolivian", "bolivia"
		}, {
			"bosnian", "bosnia"
		}, {
			"botswanan", "botswana"
		}, {
			"brazilian", "brazil"
		}, {
			"british", "britain"
		}, {
			"bulgarian", "bulgaria"
		}, {
			"burmese", "burma"
		}, {
			"californian", "california"
		}, {
			"cambodian", "cambodia"
		}, {
			"canadian", "canada"
		}, {
			"chadian", "chad"
		}, {
			"chilean", "chile"
		}, {
			"chinese", "china"
		}, {
			"colombian", "colombia"
		}, {
			"croat", "croatia"
		}, {
			"croatian", "croatia"
		}, {
			"cuban", "cuba"
		}, {
			"cypriot", "cyprus"
		}, {
			"czechoslovakian", "czechoslovakia"
		}, {
			"danish", "denmark"
		}, {
			"egyptian", "egypt"
		}, {
			"equadorian", "equador"
		}, {
			"eritrean", "eritrea"
		}, {
			"estonian", "estonia"
		}, {
			"ethiopian", "ethiopia"
		}, {
			"european", "europe"
		}, {
			"fijian", "fiji"
		}, {
			"filipino", "philippines"
		}, {
			"finnish", "finland"
		}, {
			"french", "france"
		}, {
			"gambian", "gambia"
		}, {
			"georgian", "georgia"
		}, {
			"german", "germany"
		}, {
			"ghanian", "ghana"
		}, {
			"greek", "greece"
		}, {
			"grenadan", "grenada"
		}, {
			"guamian", "guam"
		}, {
			"guatemalan", "guatemala"
		}, {
			"guinean", "guinea"
		}, {
			"guyanan", "guyana"
		}, {
			"haitian", "haiti"
		}, {
			"hawaiian", "hawaii"
		}, {
			"holland", "dutch"
		}, {
			"honduran", "honduras"
		}, {
			"hungarian", "hungary"
		}, {
			"icelandic", "iceland"
		}, {
			"indonesian", "indonesia"
		}, {
			"iranian", "iran"
		}, {
			"iraqi", "iraq"
		}, {
			"iraqui", "iraq"
		}, {
			"irish", "ireland"
		}, {
			"israeli", "israel"
		}, {
			"italian", "italy"
		}, {
			"jamaican", "jamaica"
		}, {
			"japanese", "japan"
		}, {
			"jordanian", "jordan"
		}, {
			"kampuchean", "cambodia"
		}, {
			"kenyan", "kenya"
		}, {
			"korean", "korea"
		}, {
			"kuwaiti", "kuwait"
		}, {
			"lankan", "lanka"
		}, {
			"laotian", "laos"
		}, {
			"latvian", "latvia"
		}, {
			"lebanese", "lebanon"
		}, {
			"liberian", "liberia"
		}, {
			"libyan", "libya"
		}, {
			"lithuanian", "lithuania"
		}, {
			"macedonian", "macedonia"
		}, {
			"madagascan", "madagascar"
		}, {
			"malaysian", "malaysia"
		}, {
			"maltese", "malta"
		}, {
			"mauritanian", "mauritania"
		}, {
			"mexican", "mexico"
		}, {
			"micronesian", "micronesia"
		}, {
			"moldovan", "moldova"
		}, {
			"monacan", "monaco"
		}, {
			"mongolian", "mongolia"
		}, {
			"montenegran", "montenegro"
		}, {
			"moroccan", "morocco"
		}, {
			"myanmar", "burma"
		}, {
			"namibian", "namibia"
		}, {
			"nepalese", "nepal"
		}, {
			"nicaraguan", "nicaragua"
		}, {
			"nigerian", "nigeria"
		}, {
			"norwegian", "norway"
		}, {
			"omani", "oman"
		}, {
			"pakistani", "pakistan"
		}, {
			"panamanian", "panama"
		}, {
			"papuan", "papua"
		}, {
			"paraguayan", "paraguay"
		}, {
			"peruvian", "peru"
		}, {
			"portuguese", "portugal"
		}, {
			"romanian", "romania"
		}, {
			"rumania", "romania"
		}, {
			"rumanian", "romania"
		}, {
			"russian", "russia"
		}, {
			"rwandan", "rwanda"
		}, {
			"samoan", "samoa"
		}, {
			"scottish", "scotland"
		}, {
			"serb", "serbia"
		}, {
			"serbian", "serbia"
		}, {
			"siam", "thailand"
		}, {
			"siamese", "thailand"
		}, {
			"slovakia", "slovak"
		}, {
			"slovakian", "slovak"
		}, {
			"slovenian", "slovenia"
		}, {
			"somali", "somalia"
		}, {
			"somalian", "somalia"
		}, {
			"spanish", "spain"
		}, {
			"swedish", "sweden"
		}, {
			"swiss", "switzerland"
		}, {
			"syrian", "syria"
		}, {
			"taiwanese", "taiwan"
		}, {
			"tanzanian", "tanzania"
		}, {
			"texan", "texas"
		}, {
			"thai", "thailand"
		}, {
			"tunisian", "tunisia"
		}, {
			"turkish", "turkey"
		}, {
			"ugandan", "uganda"
		}, {
			"ukrainian", "ukraine"
		}, {
			"uruguayan", "uruguay"
		}, {
			"uzbek", "uzbekistan"
		}, {
			"venezuelan", "venezuela"
		}, {
			"vietnamese", "viet"
		}, {
			"virginian", "virginia"
		}, {
			"yemeni", "yemen"
		}, {
			"yugoslav", "yugoslavia"
		}, {
			"yugoslavian", "yugoslavia"
		}, {
			"zambian", "zambia"
		}, {
			"zealander", "zealand"
		}, {
			"zimbabwean", "zimbabwe"
		}
	};
	private static final String supplementDict[] = {
		"aids", "applicator", "capacitor", "digitize", "electromagnet", "ellipsoid", "exosphere", "extensible", "ferromagnet", "graphics", 
		"hydromagnet", "polygraph", "toroid", "superconduct", "backscatter", "connectionism"
	};
	private static final String properNouns[] = {
		"abrams", "achilles", "acropolis", "adams", "agnes", "aires", "alexander", "alexis", "alfred", "algiers", 
		"alps", "amadeus", "ames", "amos", "andes", "angeles", "annapolis", "antilles", "aquarius", "archimedes", 
		"arkansas", "asher", "ashly", "athens", "atkins", "atlantis", "avis", "bahamas", "bangor", "barbados", 
		"barger", "bering", "brahms", "brandeis", "brussels", "bruxelles", "cairns", "camoros", "camus", "carlos", 
		"celts", "chalker", "charles", "cheops", "ching", "christmas", "cocos", "collins", "columbus", "confucius", 
		"conners", "connolly", "copernicus", "cramer", "cyclops", "cygnus", "cyprus", "dallas", "damascus", "daniels", 
		"davies", "davis", "decker", "denning", "dennis", "descartes", "dickens", "doris", "douglas", "downs", 
		"dreyfus", "dukakis", "dulles", "dumfries", "ecclesiastes", "edwards", "emily", "erasmus", "euphrates", "evans", 
		"everglades", "fairbanks", "federales", "fisher", "fitzsimmons", "fleming", "forbes", "fowler", "france", "francis", 
		"goering", "goodling", "goths", "grenadines", "guiness", "hades", "harding", "harris", "hastings", "hawkes", 
		"hawking", "hayes", "heights", "hercules", "himalayas", "hippocrates", "hobbs", "holmes", "honduras", "hopkins", 
		"hughes", "humphreys", "illinois", "indianapolis", "inverness", "iris", "iroquois", "irving", "isaacs", "italy", 
		"james", "jarvis", "jeffreys", "jesus", "jones", "josephus", "judas", "julius", "kansas", "keynes", 
		"kipling", "kiwanis", "lansing", "laos", "leeds", "levis", "leviticus", "lewis", "louis", "maccabees", 
		"madras", "maimonides", "maldive", "massachusetts", "matthews", "mauritius", "memphis", "mercedes", "midas", "mingus", 
		"minneapolis", "mohammed", "moines", "morris", "moses", "myers", "myknos", "nablus", "nanjing", "nantes", 
		"naples", "neal", "netherlands", "nevis", "nostradamus", "oedipus", "olympus", "orleans", "orly", "papas", 
		"paris", "parker", "pauling", "peking", "pershing", "peter", "peters", "philippines", "phineas", "pisces", 
		"pryor", "pythagoras", "queens", "rabelais", "ramses", "reynolds", "rhesus", "rhodes", "richards", "robins", 
		"rodgers", "rogers", "rubens", "sagittarius", "seychelles", "socrates", "texas", "thames", "thomas", "tiberias", 
		"tunis", "venus", "vilnius", "wales", "warner", "wilkins", "williams", "wyoming", "xmas", "yonkers", 
		"zeus", "frances", "aarhus", "adonis", "andrews", "angus", "antares", "aquinas", "arcturus", "ares", 
		"artemis", "augustus", "ayers", "barnabas", "barnes", "becker", "bejing", "biggs", "billings", "boeing", 
		"boris", "borroughs", "briggs", "buenos", "calais", "caracas", "cassius", "cerberus", "ceres", "cervantes", 
		"chantilly", "chartres", "chester", "connally", "conner", "coors", "cummings", "curtis", "daedalus", "dionysus", 
		"dobbs", "dolores", "edmonds"
	};
	private static final CharArrayMap dict_ht = initializeDictHash();
	private final OpenStringBuilder word = new OpenStringBuilder();
	private int j;
	private int k;
	DictEntry matchedEntry;
	private static char ization[] = "ization".toCharArray();
	private static char ition[] = "ition".toCharArray();
	private static char ation[] = "ation".toCharArray();
	private static char ication[] = "ication".toCharArray();
	String result;

	private char finalChar()
	{
		return word.charAt(k);
	}

	private char penultChar()
	{
		return word.charAt(k - 1);
	}

	private boolean isVowel(int index)
	{
		return !isCons(index);
	}

	private boolean isCons(int index)
	{
		char ch = word.charAt(index);
		if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u')
			return false;
		if (ch != 'y' || index == 0)
			return true;
		else
			return !isCons(index - 1);
	}

	private static CharArrayMap initializeDictHash()
	{
		CharArrayMap d = new CharArrayMap(Version.LUCENE_31, 1000, false);
		d = new CharArrayMap(Version.LUCENE_31, 1000, false);
		for (int i = 0; i < exceptionWords.length; i++)
			if (!d.containsKey(exceptionWords[i]))
			{
				DictEntry entry = new DictEntry(exceptionWords[i], true);
				d.put(exceptionWords[i], entry);
			} else
			{
				throw new RuntimeException((new StringBuilder()).append("Warning: Entry [").append(exceptionWords[i]).append("] already in dictionary 1").toString());
			}

		for (int i = 0; i < directConflations.length; i++)
			if (!d.containsKey(directConflations[i][0]))
			{
				DictEntry entry = new DictEntry(directConflations[i][1], false);
				d.put(directConflations[i][0], entry);
			} else
			{
				throw new RuntimeException((new StringBuilder()).append("Warning: Entry [").append(directConflations[i][0]).append("] already in dictionary 2").toString());
			}

		for (int i = 0; i < countryNationality.length; i++)
			if (!d.containsKey(countryNationality[i][0]))
			{
				DictEntry entry = new DictEntry(countryNationality[i][1], false);
				d.put(countryNationality[i][0], entry);
			} else
			{
				throw new RuntimeException((new StringBuilder()).append("Warning: Entry [").append(countryNationality[i][0]).append("] already in dictionary 3").toString());
			}

		DictEntry defaultEntry = new DictEntry(null, false);
		String array[] = KStemData1.data;
		for (int i = 0; i < array.length; i++)
			if (!d.containsKey(array[i]))
				d.put(array[i], defaultEntry);
			else
				throw new RuntimeException((new StringBuilder()).append("Warning: Entry [").append(array[i]).append("] already in dictionary 4").toString());

		array = KStemData2.data;
		for (int i = 0; i < array.length; i++)
			if (!d.containsKey(array[i]))
				d.put(array[i], defaultEntry);
			else
				throw new RuntimeException((new StringBuilder()).append("Warning: Entry [").append(array[i]).append("] already in dictionary 4").toString());

		array = KStemData3.data;
		for (int i = 0; i < array.length; i++)
			if (!d.containsKey(array[i]))
				d.put(array[i], defaultEntry);
			else
				throw new RuntimeException((new StringBuilder()).append("Warning: Entry [").append(array[i]).append("] already in dictionary 4").toString());

		array = KStemData4.data;
		for (int i = 0; i < array.length; i++)
			if (!d.containsKey(array[i]))
				d.put(array[i], defaultEntry);
			else
				throw new RuntimeException((new StringBuilder()).append("Warning: Entry [").append(array[i]).append("] already in dictionary 4").toString());

		array = KStemData5.data;
		for (int i = 0; i < array.length; i++)
			if (!d.containsKey(array[i]))
				d.put(array[i], defaultEntry);
			else
				throw new RuntimeException((new StringBuilder()).append("Warning: Entry [").append(array[i]).append("] already in dictionary 4").toString());

		array = KStemData6.data;
		for (int i = 0; i < array.length; i++)
			if (!d.containsKey(array[i]))
				d.put(array[i], defaultEntry);
			else
				throw new RuntimeException((new StringBuilder()).append("Warning: Entry [").append(array[i]).append("] already in dictionary 4").toString());

		array = KStemData7.data;
		for (int i = 0; i < array.length; i++)
			if (!d.containsKey(array[i]))
				d.put(array[i], defaultEntry);
			else
				throw new RuntimeException((new StringBuilder()).append("Warning: Entry [").append(array[i]).append("] already in dictionary 4").toString());

		for (int i = 0; i < KStemData8.data.length; i++)
			if (!d.containsKey(KStemData8.data[i]))
				d.put(KStemData8.data[i], defaultEntry);
			else
				throw new RuntimeException((new StringBuilder()).append("Warning: Entry [").append(KStemData8.data[i]).append("] already in dictionary 4").toString());

		for (int i = 0; i < supplementDict.length; i++)
			if (!d.containsKey(supplementDict[i]))
				d.put(supplementDict[i], defaultEntry);
			else
				throw new RuntimeException((new StringBuilder()).append("Warning: Entry [").append(supplementDict[i]).append("] already in dictionary 5").toString());

		for (int i = 0; i < properNouns.length; i++)
			if (!d.containsKey(properNouns[i]))
				d.put(properNouns[i], defaultEntry);
			else
				throw new RuntimeException((new StringBuilder()).append("Warning: Entry [").append(properNouns[i]).append("] already in dictionary 6").toString());

		return d;
	}

	private boolean isAlpha(char ch)
	{
		return ch >= 'a' && ch <= 'z';
	}

	private int stemLength()
	{
		return j + 1;
	}

	private boolean endsIn(char s[])
	{
		if (s.length > k)
			return false;
		int r = word.length() - s.length;
		j = k;
		int r1 = r;
		for (int i = 0; i < s.length;)
		{
			if (s[i] != word.charAt(r1))
				return false;
			i++;
			r1++;
		}

		j = r - 1;
		return true;
	}

	private boolean endsIn(char a, char b)
	{
		if (2 > k)
			return false;
		if (word.charAt(k - 1) == a && word.charAt(k) == b)
		{
			j = k - 2;
			return true;
		} else
		{
			return false;
		}
	}

	private boolean endsIn(char a, char b, char c)
	{
		if (3 > k)
			return false;
		if (word.charAt(k - 2) == a && word.charAt(k - 1) == b && word.charAt(k) == c)
		{
			j = k - 3;
			return true;
		} else
		{
			return false;
		}
	}

	private boolean endsIn(char a, char b, char c, char d)
	{
		if (4 > k)
			return false;
		if (word.charAt(k - 3) == a && word.charAt(k - 2) == b && word.charAt(k - 1) == c && word.charAt(k) == d)
		{
			j = k - 4;
			return true;
		} else
		{
			return false;
		}
	}

	private DictEntry wordInDict()
	{
		if (matchedEntry != null)
			return matchedEntry;
		DictEntry e = (DictEntry)dict_ht.get(word.getArray(), 0, word.length());
		if (e != null && !e.exception)
			matchedEntry = e;
		return e;
	}

	private void plural()
	{
		if (word.charAt(k) == 's')
			if (endsIn('i', 'e', 's'))
			{
				word.setLength(j + 3);
				k--;
				if (lookup())
					return;
				k++;
				word.unsafeWrite('s');
				setSuffix("y");
				lookup();
			} else
			{
				if (endsIn('e', 's'))
				{
					word.setLength(j + 2);
					k--;
					boolean tryE = j > 0 && (word.charAt(j) != 's' || word.charAt(j - 1) != 's');
					if (tryE && lookup())
						return;
					word.setLength(j + 1);
					k--;
					if (lookup())
						return;
					word.unsafeWrite('e');
					k++;
					if (!tryE)
						lookup();
					return;
				}
				if (word.length() > 3 && penultChar() != 's' && !endsIn('o', 'u', 's'))
				{
					word.setLength(k);
					k--;
					lookup();
				}
			}
	}

	private void setSuffix(String s)
	{
		setSuff(s, s.length());
	}

	private void setSuff(String s, int len)
	{
		word.setLength(j + 1);
		for (int l = 0; l < len; l++)
			word.unsafeWrite(s.charAt(l));

		k = j + len;
	}

	private boolean lookup()
	{
		matchedEntry = (DictEntry)dict_ht.get(word.getArray(), 0, word.size());
		return matchedEntry != null;
	}

	private void pastTense()
	{
		if (word.length() <= 4)
			return;
		if (endsIn('i', 'e', 'd'))
		{
			word.setLength(j + 3);
			k--;
			if (lookup())
			{
				return;
			} else
			{
				k++;
				word.unsafeWrite('d');
				setSuffix("y");
				lookup();
				return;
			}
		}
		if (endsIn('e', 'd') && vowelInStem())
		{
			word.setLength(j + 2);
			k = j + 1;
			DictEntry entry = wordInDict();
			if (entry != null && !entry.exception)
				return;
			word.setLength(j + 1);
			k = j;
			if (lookup())
				return;
			if (doubleC(k))
			{
				word.setLength(k);
				k--;
				if (lookup())
				{
					return;
				} else
				{
					word.unsafeWrite(word.charAt(k));
					k++;
					lookup();
					return;
				}
			}
			if (word.charAt(0) == 'u' && word.charAt(1) == 'n')
			{
				word.unsafeWrite('e');
				word.unsafeWrite('d');
				k = k + 2;
				return;
			} else
			{
				word.setLength(j + 1);
				word.unsafeWrite('e');
				k = j + 1;
				return;
			}
		} else
		{
			return;
		}
	}

	private boolean doubleC(int i)
	{
		if (i < 1)
			return false;
		if (word.charAt(i) != word.charAt(i - 1))
			return false;
		else
			return isCons(i);
	}

	private boolean vowelInStem()
	{
		for (int i = 0; i < stemLength(); i++)
			if (isVowel(i))
				return true;

		return false;
	}

	private void aspect()
	{
		if (word.length() <= 5)
			return;
		if (endsIn('i', 'n', 'g') && vowelInStem())
		{
			word.setCharAt(j + 1, 'e');
			word.setLength(j + 2);
			k = j + 1;
			DictEntry entry = wordInDict();
			if (entry != null && !entry.exception)
				return;
			word.setLength(k);
			k--;
			if (lookup())
				return;
			if (doubleC(k))
			{
				k--;
				word.setLength(k + 1);
				if (lookup())
				{
					return;
				} else
				{
					word.unsafeWrite(word.charAt(k));
					k++;
					lookup();
					return;
				}
			}
			if (j > 0 && isCons(j) && isCons(j - 1))
			{
				k = j;
				word.setLength(k + 1);
				return;
			} else
			{
				word.setLength(j + 1);
				word.unsafeWrite('e');
				k = j + 1;
				return;
			}
		} else
		{
			return;
		}
	}

	private void ityEndings()
	{
		int old_k = k;
		if (endsIn('i', 't', 'y'))
		{
			word.setLength(j + 1);
			k = j;
			if (lookup())
				return;
			word.unsafeWrite('e');
			k = j + 1;
			if (lookup())
				return;
			word.setCharAt(j + 1, 'i');
			word.append("ty");
			k = old_k;
			if (j > 0 && word.charAt(j - 1) == 'i' && word.charAt(j) == 'l')
			{
				word.setLength(j - 1);
				word.append("le");
				k = j;
				lookup();
				return;
			}
			if (j > 0 && word.charAt(j - 1) == 'i' && word.charAt(j) == 'v')
			{
				word.setLength(j + 1);
				word.unsafeWrite('e');
				k = j + 1;
				lookup();
				return;
			}
			if (j > 0 && word.charAt(j - 1) == 'a' && word.charAt(j) == 'l')
			{
				word.setLength(j + 1);
				k = j;
				lookup();
				return;
			}
			if (lookup())
			{
				return;
			} else
			{
				word.setLength(j + 1);
				k = j;
				return;
			}
		} else
		{
			return;
		}
	}

	private void nceEndings()
	{
		int old_k = k;
		if (endsIn('n', 'c', 'e'))
		{
			char word_char = word.charAt(j);
			if (word_char != 'e' && word_char != 'a')
				return;
			word.setLength(j);
			word.unsafeWrite('e');
			k = j;
			if (lookup())
				return;
			word.setLength(j);
			k = j - 1;
			if (lookup())
				return;
			word.unsafeWrite(word_char);
			word.append("nce");
			k = old_k;
		}
	}

	private void nessEndings()
	{
		if (endsIn('n', 'e', 's', 's'))
		{
			word.setLength(j + 1);
			k = j;
			if (word.charAt(j) == 'i')
				word.setCharAt(j, 'y');
			lookup();
		}
	}

	private void ismEndings()
	{
		if (endsIn('i', 's', 'm'))
		{
			word.setLength(j + 1);
			k = j;
			lookup();
		}
	}

	private void mentEndings()
	{
		int old_k = k;
		if (endsIn('m', 'e', 'n', 't'))
		{
			word.setLength(j + 1);
			k = j;
			if (lookup())
				return;
			word.append("ment");
			k = old_k;
		}
	}

	private void izeEndings()
	{
		int old_k = k;
		if (endsIn('i', 'z', 'e'))
		{
			word.setLength(j + 1);
			k = j;
			if (lookup())
				return;
			word.unsafeWrite('i');
			if (doubleC(j))
			{
				word.setLength(j);
				k = j - 1;
				if (lookup())
					return;
				word.unsafeWrite(word.charAt(j - 1));
			}
			word.setLength(j + 1);
			word.unsafeWrite('e');
			k = j + 1;
			if (lookup())
				return;
			word.setLength(j + 1);
			word.append("ize");
			k = old_k;
		}
	}

	private void ncyEndings()
	{
		if (endsIn('n', 'c', 'y'))
		{
			if (word.charAt(j) != 'e' && word.charAt(j) != 'a')
				return;
			word.setCharAt(j + 2, 't');
			word.setLength(j + 3);
			k = j + 2;
			if (lookup())
				return;
			word.setCharAt(j + 2, 'c');
			word.unsafeWrite('e');
			k = j + 3;
			lookup();
		}
	}

	private void bleEndings()
	{
		int old_k = k;
		if (endsIn('b', 'l', 'e'))
		{
			if (word.charAt(j) != 'a' && word.charAt(j) != 'i')
				return;
			char word_char = word.charAt(j);
			word.setLength(j);
			k = j - 1;
			if (lookup())
				return;
			if (doubleC(k))
			{
				word.setLength(k);
				k--;
				if (lookup())
					return;
				k++;
				word.unsafeWrite(word.charAt(k - 1));
			}
			word.setLength(j);
			word.unsafeWrite('e');
			k = j;
			if (lookup())
				return;
			word.setLength(j);
			word.append("ate");
			k = j + 2;
			if (lookup())
				return;
			word.setLength(j);
			word.unsafeWrite(word_char);
			word.append("ble");
			k = old_k;
		}
	}

	private void icEndings()
	{
		if (endsIn('i', 'c'))
		{
			word.setLength(j + 3);
			word.append("al");
			k = j + 4;
			if (lookup())
				return;
			word.setCharAt(j + 1, 'y');
			word.setLength(j + 2);
			k = j + 1;
			if (lookup())
				return;
			word.setCharAt(j + 1, 'e');
			if (lookup())
				return;
			word.setLength(j + 1);
			k = j;
			if (lookup())
				return;
			word.append("ic");
			k = j + 2;
		}
	}

	private void ionEndings()
	{
		int old_k = k;
		if (!endsIn('i', 'o', 'n'))
			return;
		if (endsIn(ization))
		{
			word.setLength(j + 3);
			word.unsafeWrite('e');
			k = j + 3;
			lookup();
			return;
		}
		if (endsIn(ition))
		{
			word.setLength(j + 1);
			word.unsafeWrite('e');
			k = j + 1;
			if (lookup())
				return;
			word.setLength(j + 1);
			word.append("ition");
			k = old_k;
		} else
		if (endsIn(ation))
		{
			word.setLength(j + 3);
			word.unsafeWrite('e');
			k = j + 3;
			if (lookup())
				return;
			word.setLength(j + 1);
			word.unsafeWrite('e');
			k = j + 1;
			if (lookup())
				return;
			word.setLength(j + 1);
			k = j;
			if (lookup())
				return;
			word.setLength(j + 1);
			word.append("ation");
			k = old_k;
		}
		if (endsIn(ication))
		{
			word.setLength(j + 1);
			word.unsafeWrite('y');
			k = j + 1;
			if (lookup())
				return;
			word.setLength(j + 1);
			word.append("ication");
			k = old_k;
		}
		j = k - 3;
		word.setLength(j + 1);
		word.unsafeWrite('e');
		k = j + 1;
		if (lookup())
			return;
		word.setLength(j + 1);
		k = j;
		if (lookup())
		{
			return;
		} else
		{
			word.setLength(j + 1);
			word.append("ion");
			k = old_k;
			return;
		}
	}

	private void erAndOrEndings()
	{
		int old_k = k;
		if (word.charAt(k) != 'r')
			return;
		if (endsIn('i', 'z', 'e', 'r'))
		{
			word.setLength(j + 4);
			k = j + 3;
			lookup();
			return;
		}
		if (endsIn('e', 'r') || endsIn('o', 'r'))
		{
			char word_char = word.charAt(j + 1);
			if (doubleC(j))
			{
				word.setLength(j);
				k = j - 1;
				if (lookup())
					return;
				word.unsafeWrite(word.charAt(j - 1));
			}
			if (word.charAt(j) == 'i')
			{
				word.setCharAt(j, 'y');
				word.setLength(j + 1);
				k = j;
				if (lookup())
					return;
				word.setCharAt(j, 'i');
				word.unsafeWrite('e');
			}
			if (word.charAt(j) == 'e')
			{
				word.setLength(j);
				k = j - 1;
				if (lookup())
					return;
				word.unsafeWrite('e');
			}
			word.setLength(j + 2);
			k = j + 1;
			if (lookup())
				return;
			word.setLength(j + 1);
			k = j;
			if (lookup())
				return;
			word.unsafeWrite('e');
			k = j + 1;
			if (lookup())
				return;
			word.setLength(j + 1);
			word.unsafeWrite(word_char);
			word.unsafeWrite('r');
			k = old_k;
		}
	}

	private void lyEndings()
	{
		int old_k = k;
		if (endsIn('l', 'y'))
		{
			word.setCharAt(j + 2, 'e');
			if (lookup())
				return;
			word.setCharAt(j + 2, 'y');
			word.setLength(j + 1);
			k = j;
			if (lookup())
				return;
			if (j > 0 && word.charAt(j - 1) == 'a' && word.charAt(j) == 'l')
				return;
			word.append("ly");
			k = old_k;
			if (j > 0 && word.charAt(j - 1) == 'a' && word.charAt(j) == 'b')
			{
				word.setCharAt(j + 2, 'e');
				k = j + 2;
				return;
			}
			if (word.charAt(j) == 'i')
			{
				word.setLength(j);
				word.unsafeWrite('y');
				k = j;
				if (lookup())
					return;
				word.setLength(j);
				word.append("ily");
				k = old_k;
			}
			word.setLength(j + 1);
			k = j;
		}
	}

	private void alEndings()
	{
		int old_k = k;
		if (word.length() < 4)
			return;
		if (endsIn('a', 'l'))
		{
			word.setLength(j + 1);
			k = j;
			if (lookup())
				return;
			if (doubleC(j))
			{
				word.setLength(j);
				k = j - 1;
				if (lookup())
					return;
				word.unsafeWrite(word.charAt(j - 1));
			}
			word.setLength(j + 1);
			word.unsafeWrite('e');
			k = j + 1;
			if (lookup())
				return;
			word.setLength(j + 1);
			word.append("um");
			k = j + 2;
			if (lookup())
				return;
			word.setLength(j + 1);
			word.append("al");
			k = old_k;
			if (j > 0 && word.charAt(j - 1) == 'i' && word.charAt(j) == 'c')
			{
				word.setLength(j - 1);
				k = j - 2;
				if (lookup())
					return;
				word.setLength(j - 1);
				word.unsafeWrite('y');
				k = j - 1;
				if (lookup())
				{
					return;
				} else
				{
					word.setLength(j - 1);
					word.append("ic");
					k = j;
					lookup();
					return;
				}
			}
			if (word.charAt(j) == 'i')
			{
				word.setLength(j);
				k = j - 1;
				if (lookup())
					return;
				word.append("ial");
				k = old_k;
				lookup();
			}
		}
	}

	private void iveEndings()
	{
		int old_k = k;
		if (endsIn('i', 'v', 'e'))
		{
			word.setLength(j + 1);
			k = j;
			if (lookup())
				return;
			word.unsafeWrite('e');
			k = j + 1;
			if (lookup())
				return;
			word.setLength(j + 1);
			word.append("ive");
			if (j > 0 && word.charAt(j - 1) == 'a' && word.charAt(j) == 't')
			{
				word.setCharAt(j - 1, 'e');
				word.setLength(j);
				k = j - 1;
				if (lookup())
					return;
				word.setLength(j - 1);
				if (lookup())
					return;
				word.append("ative");
				k = old_k;
			}
			word.setCharAt(j + 2, 'o');
			word.setCharAt(j + 3, 'n');
			if (lookup())
				return;
			word.setCharAt(j + 2, 'v');
			word.setCharAt(j + 3, 'e');
			k = old_k;
		}
	}

	KStemmer()
	{
		matchedEntry = null;
	}

	String stem(String term)
	{
		boolean changed = stem(term.toCharArray(), term.length());
		if (!changed)
			return term;
		else
			return asString();
	}

	String asString()
	{
		String s = getString();
		if (s != null)
			return s;
		else
			return word.toString();
	}

	CharSequence asCharSequence()
	{
		return ((CharSequence) (result == null ? word : result));
	}

	String getString()
	{
		return result;
	}

	char[] getChars()
	{
		return word.getArray();
	}

	int getLength()
	{
		return word.length();
	}

	private boolean matched()
	{
		return matchedEntry != null;
	}

	boolean stem(char term[], int len)
	{
		result = null;
		k = len - 1;
		if (k <= 1 || k >= 49)
			return false;
		DictEntry entry = (DictEntry)dict_ht.get(term, 0, len);
		if (entry != null)
			if (entry.root != null)
			{
				result = entry.root;
				return true;
			} else
			{
				return false;
			}
		word.reset();
		word.reserve(len + 10);
		for (int i = 0; i < len; i++)
		{
			char ch = term[i];
			if (!isAlpha(ch))
				return false;
			word.unsafeWrite(ch);
		}

		matchedEntry = null;
		plural();
		if (!matched())
		{
			pastTense();
			if (!matched())
			{
				aspect();
				if (!matched())
				{
					ityEndings();
					if (!matched())
					{
						nessEndings();
						if (!matched())
						{
							ionEndings();
							if (!matched())
							{
								erAndOrEndings();
								if (!matched())
								{
									lyEndings();
									if (!matched())
									{
										alEndings();
										if (!matched())
										{
											entry = wordInDict();
											iveEndings();
											if (!matched())
											{
												izeEndings();
												if (!matched())
												{
													mentEndings();
													if (!matched())
													{
														bleEndings();
														if (!matched())
														{
															ismEndings();
															if (!matched())
															{
																icEndings();
																if (!matched())
																{
																	ncyEndings();
																	if (!matched())
																	{
																		nceEndings();
																		matched();
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		entry = matchedEntry;
		if (entry != null)
			result = entry.root;
		return true;
	}

}
