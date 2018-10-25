// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ASCIIFoldingFilter.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.ArrayUtil;

public final class ASCIIFoldingFilter extends TokenFilter
{

	private char output[];
	private int outputPos;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);

	public ASCIIFoldingFilter(TokenStream input)
	{
		super(input);
		output = new char[512];
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			char buffer[] = termAtt.buffer();
			int length = termAtt.length();
			int i = 0;
			do
			{
				if (i >= length)
					break;
				char c = buffer[i];
				if (c >= '\200')
				{
					foldToASCII(buffer, length);
					termAtt.copyBuffer(output, 0, outputPos);
					break;
				}
				i++;
			} while (true);
			return true;
		} else
		{
			return false;
		}
	}

	public void foldToASCII(char input[], int length)
	{
		int maxSizeNeeded = 4 * length;
		if (output.length < maxSizeNeeded)
			output = new char[ArrayUtil.oversize(maxSizeNeeded, 2)];
		outputPos = foldToASCII(input, 0, output, 0, length);
	}

	public static final int foldToASCII(char input[], int inputPos, char output[], int outputPos, int length)
	{
		int end = inputPos + length;
		for (int pos = inputPos; pos < end; pos++)
		{
			char c = input[pos];
			if (c < '\200')
			{
				output[outputPos++] = c;
				continue;
			}
			switch (c)
			{
			case 192: 
			case 193: 
			case 194: 
			case 195: 
			case 196: 
			case 197: 
			case 256: 
			case 258: 
			case 260: 
			case 399: 
			case 461: 
			case 478: 
			case 480: 
			case 506: 
			case 512: 
			case 514: 
			case 550: 
			case 570: 
			case 7424: 
			case 7680: 
			case 7840: 
			case 7842: 
			case 7844: 
			case 7846: 
			case 7848: 
			case 7850: 
			case 7852: 
			case 7854: 
			case 7856: 
			case 7858: 
			case 7860: 
			case 7862: 
			case 9398: 
			case 65313: 
				output[outputPos++] = 'A';
				break;

			case 224: 
			case 225: 
			case 226: 
			case 227: 
			case 228: 
			case 229: 
			case 257: 
			case 259: 
			case 261: 
			case 462: 
			case 479: 
			case 481: 
			case 507: 
			case 513: 
			case 515: 
			case 551: 
			case 592: 
			case 601: 
			case 602: 
			case 7567: 
			case 7573: 
			case 7681: 
			case 7834: 
			case 7841: 
			case 7843: 
			case 7845: 
			case 7847: 
			case 7849: 
			case 7851: 
			case 7853: 
			case 7855: 
			case 7857: 
			case 7859: 
			case 7861: 
			case 7863: 
			case 8336: 
			case 8340: 
			case 9424: 
			case 11365: 
			case 11375: 
			case 65345: 
				output[outputPos++] = 'a';
				break;

			case 42802: 
				output[outputPos++] = 'A';
				output[outputPos++] = 'A';
				break;

			case 198: 
			case 482: 
			case 508: 
			case 7425: 
				output[outputPos++] = 'A';
				output[outputPos++] = 'E';
				break;

			case 42804: 
				output[outputPos++] = 'A';
				output[outputPos++] = 'O';
				break;

			case 42806: 
				output[outputPos++] = 'A';
				output[outputPos++] = 'U';
				break;

			case 42808: 
			case 42810: 
				output[outputPos++] = 'A';
				output[outputPos++] = 'V';
				break;

			case 42812: 
				output[outputPos++] = 'A';
				output[outputPos++] = 'Y';
				break;

			case 9372: 
				output[outputPos++] = '(';
				output[outputPos++] = 'a';
				output[outputPos++] = ')';
				break;

			case 42803: 
				output[outputPos++] = 'a';
				output[outputPos++] = 'a';
				break;

			case 230: 
			case 483: 
			case 509: 
			case 7426: 
				output[outputPos++] = 'a';
				output[outputPos++] = 'e';
				break;

			case 42805: 
				output[outputPos++] = 'a';
				output[outputPos++] = 'o';
				break;

			case 42807: 
				output[outputPos++] = 'a';
				output[outputPos++] = 'u';
				break;

			case 42809: 
			case 42811: 
				output[outputPos++] = 'a';
				output[outputPos++] = 'v';
				break;

			case 42813: 
				output[outputPos++] = 'a';
				output[outputPos++] = 'y';
				break;

			case 385: 
			case 386: 
			case 579: 
			case 665: 
			case 7427: 
			case 7682: 
			case 7684: 
			case 7686: 
			case 9399: 
			case 65314: 
				output[outputPos++] = 'B';
				break;

			case 384: 
			case 387: 
			case 595: 
			case 7532: 
			case 7552: 
			case 7683: 
			case 7685: 
			case 7687: 
			case 9425: 
			case 65346: 
				output[outputPos++] = 'b';
				break;

			case 9373: 
				output[outputPos++] = '(';
				output[outputPos++] = 'b';
				output[outputPos++] = ')';
				break;

			case 199: 
			case 262: 
			case 264: 
			case 266: 
			case 268: 
			case 391: 
			case 571: 
			case 663: 
			case 7428: 
			case 7688: 
			case 9400: 
			case 65315: 
				output[outputPos++] = 'C';
				break;

			case 231: 
			case 263: 
			case 265: 
			case 267: 
			case 269: 
			case 392: 
			case 572: 
			case 597: 
			case 7689: 
			case 8580: 
			case 9426: 
			case 42814: 
			case 42815: 
			case 65347: 
				output[outputPos++] = 'c';
				break;

			case 9374: 
				output[outputPos++] = '(';
				output[outputPos++] = 'c';
				output[outputPos++] = ')';
				break;

			case 208: 
			case 270: 
			case 272: 
			case 393: 
			case 394: 
			case 395: 
			case 7429: 
			case 7430: 
			case 7690: 
			case 7692: 
			case 7694: 
			case 7696: 
			case 7698: 
			case 9401: 
			case 42873: 
			case 65316: 
				output[outputPos++] = 'D';
				break;

			case 240: 
			case 271: 
			case 273: 
			case 396: 
			case 545: 
			case 598: 
			case 599: 
			case 7533: 
			case 7553: 
			case 7569: 
			case 7691: 
			case 7693: 
			case 7695: 
			case 7697: 
			case 7699: 
			case 9427: 
			case 42874: 
			case 65348: 
				output[outputPos++] = 'd';
				break;

			case 452: 
			case 497: 
				output[outputPos++] = 'D';
				output[outputPos++] = 'Z';
				break;

			case 453: 
			case 498: 
				output[outputPos++] = 'D';
				output[outputPos++] = 'z';
				break;

			case 9375: 
				output[outputPos++] = '(';
				output[outputPos++] = 'd';
				output[outputPos++] = ')';
				break;

			case 568: 
				output[outputPos++] = 'd';
				output[outputPos++] = 'b';
				break;

			case 454: 
			case 499: 
			case 675: 
			case 677: 
				output[outputPos++] = 'd';
				output[outputPos++] = 'z';
				break;

			case 200: 
			case 201: 
			case 202: 
			case 203: 
			case 274: 
			case 276: 
			case 278: 
			case 280: 
			case 282: 
			case 398: 
			case 400: 
			case 516: 
			case 518: 
			case 552: 
			case 582: 
			case 7431: 
			case 7700: 
			case 7702: 
			case 7704: 
			case 7706: 
			case 7708: 
			case 7864: 
			case 7866: 
			case 7868: 
			case 7870: 
			case 7872: 
			case 7874: 
			case 7876: 
			case 7878: 
			case 9402: 
			case 11387: 
			case 65317: 
				output[outputPos++] = 'E';
				break;

			case 232: 
			case 233: 
			case 234: 
			case 235: 
			case 275: 
			case 277: 
			case 279: 
			case 281: 
			case 283: 
			case 477: 
			case 517: 
			case 519: 
			case 553: 
			case 583: 
			case 600: 
			case 603: 
			case 604: 
			case 605: 
			case 606: 
			case 666: 
			case 7432: 
			case 7570: 
			case 7571: 
			case 7572: 
			case 7701: 
			case 7703: 
			case 7705: 
			case 7707: 
			case 7709: 
			case 7865: 
			case 7867: 
			case 7869: 
			case 7871: 
			case 7873: 
			case 7875: 
			case 7877: 
			case 7879: 
			case 8337: 
			case 9428: 
			case 11384: 
			case 65349: 
				output[outputPos++] = 'e';
				break;

			case 9376: 
				output[outputPos++] = '(';
				output[outputPos++] = 'e';
				output[outputPos++] = ')';
				break;

			case 401: 
			case 7710: 
			case 9403: 
			case 42800: 
			case 42875: 
			case 43003: 
			case 65318: 
				output[outputPos++] = 'F';
				break;

			case 402: 
			case 7534: 
			case 7554: 
			case 7711: 
			case 7835: 
			case 9429: 
			case 42876: 
			case 65350: 
				output[outputPos++] = 'f';
				break;

			case 9377: 
				output[outputPos++] = '(';
				output[outputPos++] = 'f';
				output[outputPos++] = ')';
				break;

			case 64256: 
				output[outputPos++] = 'f';
				output[outputPos++] = 'f';
				break;

			case 64259: 
				output[outputPos++] = 'f';
				output[outputPos++] = 'f';
				output[outputPos++] = 'i';
				break;

			case 64260: 
				output[outputPos++] = 'f';
				output[outputPos++] = 'f';
				output[outputPos++] = 'l';
				break;

			case 64257: 
				output[outputPos++] = 'f';
				output[outputPos++] = 'i';
				break;

			case 64258: 
				output[outputPos++] = 'f';
				output[outputPos++] = 'l';
				break;

			case 284: 
			case 286: 
			case 288: 
			case 290: 
			case 403: 
			case 484: 
			case 485: 
			case 486: 
			case 487: 
			case 500: 
			case 610: 
			case 667: 
			case 7712: 
			case 9404: 
			case 42877: 
			case 42878: 
			case 65319: 
				output[outputPos++] = 'G';
				break;

			case 285: 
			case 287: 
			case 289: 
			case 291: 
			case 501: 
			case 608: 
			case 609: 
			case 7543: 
			case 7545: 
			case 7555: 
			case 7713: 
			case 9430: 
			case 42879: 
			case 65351: 
				output[outputPos++] = 'g';
				break;

			case 9378: 
				output[outputPos++] = '(';
				output[outputPos++] = 'g';
				output[outputPos++] = ')';
				break;

			case 292: 
			case 294: 
			case 542: 
			case 668: 
			case 7714: 
			case 7716: 
			case 7718: 
			case 7720: 
			case 7722: 
			case 9405: 
			case 11367: 
			case 11381: 
			case 65320: 
				output[outputPos++] = 'H';
				break;

			case 293: 
			case 295: 
			case 543: 
			case 613: 
			case 614: 
			case 686: 
			case 687: 
			case 7715: 
			case 7717: 
			case 7719: 
			case 7721: 
			case 7723: 
			case 7830: 
			case 9431: 
			case 11368: 
			case 11382: 
			case 65352: 
				output[outputPos++] = 'h';
				break;

			case 502: 
				output[outputPos++] = 'H';
				output[outputPos++] = 'V';
				break;

			case 9379: 
				output[outputPos++] = '(';
				output[outputPos++] = 'h';
				output[outputPos++] = ')';
				break;

			case 405: 
				output[outputPos++] = 'h';
				output[outputPos++] = 'v';
				break;

			case 204: 
			case 205: 
			case 206: 
			case 207: 
			case 296: 
			case 298: 
			case 300: 
			case 302: 
			case 304: 
			case 406: 
			case 407: 
			case 463: 
			case 520: 
			case 522: 
			case 618: 
			case 7547: 
			case 7724: 
			case 7726: 
			case 7880: 
			case 7882: 
			case 9406: 
			case 43006: 
			case 65321: 
				output[outputPos++] = 'I';
				break;

			case 236: 
			case 237: 
			case 238: 
			case 239: 
			case 297: 
			case 299: 
			case 301: 
			case 303: 
			case 305: 
			case 464: 
			case 521: 
			case 523: 
			case 616: 
			case 7433: 
			case 7522: 
			case 7548: 
			case 7574: 
			case 7725: 
			case 7727: 
			case 7881: 
			case 7883: 
			case 8305: 
			case 9432: 
			case 65353: 
				output[outputPos++] = 'i';
				break;

			case 306: 
				output[outputPos++] = 'I';
				output[outputPos++] = 'J';
				break;

			case 9380: 
				output[outputPos++] = '(';
				output[outputPos++] = 'i';
				output[outputPos++] = ')';
				break;

			case 307: 
				output[outputPos++] = 'i';
				output[outputPos++] = 'j';
				break;

			case 308: 
			case 584: 
			case 7434: 
			case 9407: 
			case 65322: 
				output[outputPos++] = 'J';
				break;

			case 309: 
			case 496: 
			case 567: 
			case 585: 
			case 607: 
			case 644: 
			case 669: 
			case 9433: 
			case 11388: 
			case 65354: 
				output[outputPos++] = 'j';
				break;

			case 9381: 
				output[outputPos++] = '(';
				output[outputPos++] = 'j';
				output[outputPos++] = ')';
				break;

			case 310: 
			case 408: 
			case 488: 
			case 7435: 
			case 7728: 
			case 7730: 
			case 7732: 
			case 9408: 
			case 11369: 
			case 42816: 
			case 42818: 
			case 42820: 
			case 65323: 
				output[outputPos++] = 'K';
				break;

			case 311: 
			case 409: 
			case 489: 
			case 670: 
			case 7556: 
			case 7729: 
			case 7731: 
			case 7733: 
			case 9434: 
			case 11370: 
			case 42817: 
			case 42819: 
			case 42821: 
			case 65355: 
				output[outputPos++] = 'k';
				break;

			case 9382: 
				output[outputPos++] = '(';
				output[outputPos++] = 'k';
				output[outputPos++] = ')';
				break;

			case 313: 
			case 315: 
			case 317: 
			case 319: 
			case 321: 
			case 573: 
			case 671: 
			case 7436: 
			case 7734: 
			case 7736: 
			case 7738: 
			case 7740: 
			case 9409: 
			case 11360: 
			case 11362: 
			case 42822: 
			case 42824: 
			case 42880: 
			case 65324: 
				output[outputPos++] = 'L';
				break;

			case 314: 
			case 316: 
			case 318: 
			case 320: 
			case 322: 
			case 410: 
			case 564: 
			case 619: 
			case 620: 
			case 621: 
			case 7557: 
			case 7735: 
			case 7737: 
			case 7739: 
			case 7741: 
			case 9435: 
			case 11361: 
			case 42823: 
			case 42825: 
			case 42881: 
			case 65356: 
				output[outputPos++] = 'l';
				break;

			case 455: 
				output[outputPos++] = 'L';
				output[outputPos++] = 'J';
				break;

			case 7930: 
				output[outputPos++] = 'L';
				output[outputPos++] = 'L';
				break;

			case 456: 
				output[outputPos++] = 'L';
				output[outputPos++] = 'j';
				break;

			case 9383: 
				output[outputPos++] = '(';
				output[outputPos++] = 'l';
				output[outputPos++] = ')';
				break;

			case 457: 
				output[outputPos++] = 'l';
				output[outputPos++] = 'j';
				break;

			case 7931: 
				output[outputPos++] = 'l';
				output[outputPos++] = 'l';
				break;

			case 682: 
				output[outputPos++] = 'l';
				output[outputPos++] = 's';
				break;

			case 683: 
				output[outputPos++] = 'l';
				output[outputPos++] = 'z';
				break;

			case 412: 
			case 7437: 
			case 7742: 
			case 7744: 
			case 7746: 
			case 9410: 
			case 11374: 
			case 43005: 
			case 43007: 
			case 65325: 
				output[outputPos++] = 'M';
				break;

			case 623: 
			case 624: 
			case 625: 
			case 7535: 
			case 7558: 
			case 7743: 
			case 7745: 
			case 7747: 
			case 9436: 
			case 65357: 
				output[outputPos++] = 'm';
				break;

			case 9384: 
				output[outputPos++] = '(';
				output[outputPos++] = 'm';
				output[outputPos++] = ')';
				break;

			case 209: 
			case 323: 
			case 325: 
			case 327: 
			case 330: 
			case 413: 
			case 504: 
			case 544: 
			case 628: 
			case 7438: 
			case 7748: 
			case 7750: 
			case 7752: 
			case 7754: 
			case 9411: 
			case 65326: 
				output[outputPos++] = 'N';
				break;

			case 241: 
			case 324: 
			case 326: 
			case 328: 
			case 329: 
			case 331: 
			case 414: 
			case 505: 
			case 565: 
			case 626: 
			case 627: 
			case 7536: 
			case 7559: 
			case 7749: 
			case 7751: 
			case 7753: 
			case 7755: 
			case 8319: 
			case 9437: 
			case 65358: 
				output[outputPos++] = 'n';
				break;

			case 458: 
				output[outputPos++] = 'N';
				output[outputPos++] = 'J';
				break;

			case 459: 
				output[outputPos++] = 'N';
				output[outputPos++] = 'j';
				break;

			case 9385: 
				output[outputPos++] = '(';
				output[outputPos++] = 'n';
				output[outputPos++] = ')';
				break;

			case 460: 
				output[outputPos++] = 'n';
				output[outputPos++] = 'j';
				break;

			case 210: 
			case 211: 
			case 212: 
			case 213: 
			case 214: 
			case 216: 
			case 332: 
			case 334: 
			case 336: 
			case 390: 
			case 415: 
			case 416: 
			case 465: 
			case 490: 
			case 492: 
			case 510: 
			case 524: 
			case 526: 
			case 554: 
			case 556: 
			case 558: 
			case 560: 
			case 7439: 
			case 7440: 
			case 7756: 
			case 7758: 
			case 7760: 
			case 7762: 
			case 7884: 
			case 7886: 
			case 7888: 
			case 7890: 
			case 7892: 
			case 7894: 
			case 7896: 
			case 7898: 
			case 7900: 
			case 7902: 
			case 7904: 
			case 7906: 
			case 9412: 
			case 42826: 
			case 42828: 
			case 65327: 
				output[outputPos++] = 'O';
				break;

			case 242: 
			case 243: 
			case 244: 
			case 245: 
			case 246: 
			case 248: 
			case 333: 
			case 335: 
			case 337: 
			case 417: 
			case 466: 
			case 491: 
			case 493: 
			case 511: 
			case 525: 
			case 527: 
			case 555: 
			case 557: 
			case 559: 
			case 561: 
			case 596: 
			case 629: 
			case 7446: 
			case 7447: 
			case 7575: 
			case 7757: 
			case 7759: 
			case 7761: 
			case 7763: 
			case 7885: 
			case 7887: 
			case 7889: 
			case 7891: 
			case 7893: 
			case 7895: 
			case 7897: 
			case 7899: 
			case 7901: 
			case 7903: 
			case 7905: 
			case 7907: 
			case 8338: 
			case 9438: 
			case 11386: 
			case 42827: 
			case 42829: 
			case 65359: 
				output[outputPos++] = 'o';
				break;

			case 338: 
			case 630: 
				output[outputPos++] = 'O';
				output[outputPos++] = 'E';
				break;

			case 42830: 
				output[outputPos++] = 'O';
				output[outputPos++] = 'O';
				break;

			case 546: 
			case 7445: 
				output[outputPos++] = 'O';
				output[outputPos++] = 'U';
				break;

			case 9386: 
				output[outputPos++] = '(';
				output[outputPos++] = 'o';
				output[outputPos++] = ')';
				break;

			case 339: 
			case 7444: 
				output[outputPos++] = 'o';
				output[outputPos++] = 'e';
				break;

			case 42831: 
				output[outputPos++] = 'o';
				output[outputPos++] = 'o';
				break;

			case 547: 
				output[outputPos++] = 'o';
				output[outputPos++] = 'u';
				break;

			case 420: 
			case 7448: 
			case 7764: 
			case 7766: 
			case 9413: 
			case 11363: 
			case 42832: 
			case 42834: 
			case 42836: 
			case 65328: 
				output[outputPos++] = 'P';
				break;

			case 421: 
			case 7537: 
			case 7549: 
			case 7560: 
			case 7765: 
			case 7767: 
			case 9439: 
			case 42833: 
			case 42835: 
			case 42837: 
			case 43004: 
			case 65360: 
				output[outputPos++] = 'p';
				break;

			case 9387: 
				output[outputPos++] = '(';
				output[outputPos++] = 'p';
				output[outputPos++] = ')';
				break;

			case 586: 
			case 9414: 
			case 42838: 
			case 42840: 
			case 65329: 
				output[outputPos++] = 'Q';
				break;

			case 312: 
			case 587: 
			case 672: 
			case 9440: 
			case 42839: 
			case 42841: 
			case 65361: 
				output[outputPos++] = 'q';
				break;

			case 9388: 
				output[outputPos++] = '(';
				output[outputPos++] = 'q';
				output[outputPos++] = ')';
				break;

			case 569: 
				output[outputPos++] = 'q';
				output[outputPos++] = 'p';
				break;

			case 340: 
			case 342: 
			case 344: 
			case 528: 
			case 530: 
			case 588: 
			case 640: 
			case 641: 
			case 7449: 
			case 7450: 
			case 7768: 
			case 7770: 
			case 7772: 
			case 7774: 
			case 9415: 
			case 11364: 
			case 42842: 
			case 42882: 
			case 65330: 
				output[outputPos++] = 'R';
				break;

			case 341: 
			case 343: 
			case 345: 
			case 529: 
			case 531: 
			case 589: 
			case 636: 
			case 637: 
			case 638: 
			case 639: 
			case 7523: 
			case 7538: 
			case 7539: 
			case 7561: 
			case 7769: 
			case 7771: 
			case 7773: 
			case 7775: 
			case 9441: 
			case 42843: 
			case 42883: 
			case 65362: 
				output[outputPos++] = 'r';
				break;

			case 9389: 
				output[outputPos++] = '(';
				output[outputPos++] = 'r';
				output[outputPos++] = ')';
				break;

			case 346: 
			case 348: 
			case 350: 
			case 352: 
			case 536: 
			case 7776: 
			case 7778: 
			case 7780: 
			case 7782: 
			case 7784: 
			case 9416: 
			case 42801: 
			case 42885: 
			case 65331: 
				output[outputPos++] = 'S';
				break;

			case 347: 
			case 349: 
			case 351: 
			case 353: 
			case 383: 
			case 537: 
			case 575: 
			case 642: 
			case 7540: 
			case 7562: 
			case 7777: 
			case 7779: 
			case 7781: 
			case 7783: 
			case 7785: 
			case 7836: 
			case 7837: 
			case 9442: 
			case 42884: 
			case 65363: 
				output[outputPos++] = 's';
				break;

			case 7838: 
				output[outputPos++] = 'S';
				output[outputPos++] = 'S';
				break;

			case 9390: 
				output[outputPos++] = '(';
				output[outputPos++] = 's';
				output[outputPos++] = ')';
				break;

			case 223: 
				output[outputPos++] = 's';
				output[outputPos++] = 's';
				break;

			case 64262: 
				output[outputPos++] = 's';
				output[outputPos++] = 't';
				break;

			case 354: 
			case 356: 
			case 358: 
			case 428: 
			case 430: 
			case 538: 
			case 574: 
			case 7451: 
			case 7786: 
			case 7788: 
			case 7790: 
			case 7792: 
			case 9417: 
			case 42886: 
			case 65332: 
				output[outputPos++] = 'T';
				break;

			case 355: 
			case 357: 
			case 359: 
			case 427: 
			case 429: 
			case 539: 
			case 566: 
			case 647: 
			case 648: 
			case 7541: 
			case 7787: 
			case 7789: 
			case 7791: 
			case 7793: 
			case 7831: 
			case 9443: 
			case 11366: 
			case 65364: 
				output[outputPos++] = 't';
				break;

			case 222: 
			case 42854: 
				output[outputPos++] = 'T';
				output[outputPos++] = 'H';
				break;

			case 42792: 
				output[outputPos++] = 'T';
				output[outputPos++] = 'Z';
				break;

			case 9391: 
				output[outputPos++] = '(';
				output[outputPos++] = 't';
				output[outputPos++] = ')';
				break;

			case 680: 
				output[outputPos++] = 't';
				output[outputPos++] = 'c';
				break;

			case 254: 
			case 7546: 
			case 42855: 
				output[outputPos++] = 't';
				output[outputPos++] = 'h';
				break;

			case 678: 
				output[outputPos++] = 't';
				output[outputPos++] = 's';
				break;

			case 42793: 
				output[outputPos++] = 't';
				output[outputPos++] = 'z';
				break;

			case 217: 
			case 218: 
			case 219: 
			case 220: 
			case 360: 
			case 362: 
			case 364: 
			case 366: 
			case 368: 
			case 370: 
			case 431: 
			case 467: 
			case 469: 
			case 471: 
			case 473: 
			case 475: 
			case 532: 
			case 534: 
			case 580: 
			case 7452: 
			case 7550: 
			case 7794: 
			case 7796: 
			case 7798: 
			case 7800: 
			case 7802: 
			case 7908: 
			case 7910: 
			case 7912: 
			case 7914: 
			case 7916: 
			case 7918: 
			case 7920: 
			case 9418: 
			case 65333: 
				output[outputPos++] = 'U';
				break;

			case 249: 
			case 250: 
			case 251: 
			case 252: 
			case 361: 
			case 363: 
			case 365: 
			case 367: 
			case 369: 
			case 371: 
			case 432: 
			case 468: 
			case 470: 
			case 472: 
			case 474: 
			case 476: 
			case 533: 
			case 535: 
			case 649: 
			case 7524: 
			case 7577: 
			case 7795: 
			case 7797: 
			case 7799: 
			case 7801: 
			case 7803: 
			case 7909: 
			case 7911: 
			case 7913: 
			case 7915: 
			case 7917: 
			case 7919: 
			case 7921: 
			case 9444: 
			case 65365: 
				output[outputPos++] = 'u';
				break;

			case 9392: 
				output[outputPos++] = '(';
				output[outputPos++] = 'u';
				output[outputPos++] = ')';
				break;

			case 7531: 
				output[outputPos++] = 'u';
				output[outputPos++] = 'e';
				break;

			case 434: 
			case 581: 
			case 7456: 
			case 7804: 
			case 7806: 
			case 7932: 
			case 9419: 
			case 42846: 
			case 42856: 
			case 65334: 
				output[outputPos++] = 'V';
				break;

			case 651: 
			case 652: 
			case 7525: 
			case 7564: 
			case 7805: 
			case 7807: 
			case 9445: 
			case 11377: 
			case 11380: 
			case 42847: 
			case 65366: 
				output[outputPos++] = 'v';
				break;

			case 42848: 
				output[outputPos++] = 'V';
				output[outputPos++] = 'Y';
				break;

			case 9393: 
				output[outputPos++] = '(';
				output[outputPos++] = 'v';
				output[outputPos++] = ')';
				break;

			case 42849: 
				output[outputPos++] = 'v';
				output[outputPos++] = 'y';
				break;

			case 372: 
			case 503: 
			case 7457: 
			case 7808: 
			case 7810: 
			case 7812: 
			case 7814: 
			case 7816: 
			case 9420: 
			case 11378: 
			case 65335: 
				output[outputPos++] = 'W';
				break;

			case 373: 
			case 447: 
			case 653: 
			case 7809: 
			case 7811: 
			case 7813: 
			case 7815: 
			case 7817: 
			case 7832: 
			case 9446: 
			case 11379: 
			case 65367: 
				output[outputPos++] = 'w';
				break;

			case 9394: 
				output[outputPos++] = '(';
				output[outputPos++] = 'w';
				output[outputPos++] = ')';
				break;

			case 7818: 
			case 7820: 
			case 9421: 
			case 65336: 
				output[outputPos++] = 'X';
				break;

			case 7565: 
			case 7819: 
			case 7821: 
			case 8339: 
			case 9447: 
			case 65368: 
				output[outputPos++] = 'x';
				break;

			case 9395: 
				output[outputPos++] = '(';
				output[outputPos++] = 'x';
				output[outputPos++] = ')';
				break;

			case 221: 
			case 374: 
			case 376: 
			case 435: 
			case 562: 
			case 590: 
			case 655: 
			case 7822: 
			case 7922: 
			case 7924: 
			case 7926: 
			case 7928: 
			case 7934: 
			case 9422: 
			case 65337: 
				output[outputPos++] = 'Y';
				break;

			case 253: 
			case 255: 
			case 375: 
			case 436: 
			case 563: 
			case 591: 
			case 654: 
			case 7823: 
			case 7833: 
			case 7923: 
			case 7925: 
			case 7927: 
			case 7929: 
			case 7935: 
			case 9448: 
			case 65369: 
				output[outputPos++] = 'y';
				break;

			case 9396: 
				output[outputPos++] = '(';
				output[outputPos++] = 'y';
				output[outputPos++] = ')';
				break;

			case 377: 
			case 379: 
			case 381: 
			case 437: 
			case 540: 
			case 548: 
			case 7458: 
			case 7824: 
			case 7826: 
			case 7828: 
			case 9423: 
			case 11371: 
			case 42850: 
			case 65338: 
				output[outputPos++] = 'Z';
				break;

			case 378: 
			case 380: 
			case 382: 
			case 438: 
			case 541: 
			case 549: 
			case 576: 
			case 656: 
			case 657: 
			case 7542: 
			case 7566: 
			case 7825: 
			case 7827: 
			case 7829: 
			case 9449: 
			case 11372: 
			case 42851: 
			case 65370: 
				output[outputPos++] = 'z';
				break;

			case 9397: 
				output[outputPos++] = '(';
				output[outputPos++] = 'z';
				output[outputPos++] = ')';
				break;

			case 8304: 
			case 8320: 
			case 9450: 
			case 9471: 
			case 65296: 
				output[outputPos++] = '0';
				break;

			case 185: 
			case 8321: 
			case 9312: 
			case 9461: 
			case 10102: 
			case 10112: 
			case 10122: 
			case 65297: 
				output[outputPos++] = '1';
				break;

			case 9352: 
				output[outputPos++] = '1';
				output[outputPos++] = '.';
				break;

			case 9332: 
				output[outputPos++] = '(';
				output[outputPos++] = '1';
				output[outputPos++] = ')';
				break;

			case 178: 
			case 8322: 
			case 9313: 
			case 9462: 
			case 10103: 
			case 10113: 
			case 10123: 
			case 65298: 
				output[outputPos++] = '2';
				break;

			case 9353: 
				output[outputPos++] = '2';
				output[outputPos++] = '.';
				break;

			case 9333: 
				output[outputPos++] = '(';
				output[outputPos++] = '2';
				output[outputPos++] = ')';
				break;

			case 179: 
			case 8323: 
			case 9314: 
			case 9463: 
			case 10104: 
			case 10114: 
			case 10124: 
			case 65299: 
				output[outputPos++] = '3';
				break;

			case 9354: 
				output[outputPos++] = '3';
				output[outputPos++] = '.';
				break;

			case 9334: 
				output[outputPos++] = '(';
				output[outputPos++] = '3';
				output[outputPos++] = ')';
				break;

			case 8308: 
			case 8324: 
			case 9315: 
			case 9464: 
			case 10105: 
			case 10115: 
			case 10125: 
			case 65300: 
				output[outputPos++] = '4';
				break;

			case 9355: 
				output[outputPos++] = '4';
				output[outputPos++] = '.';
				break;

			case 9335: 
				output[outputPos++] = '(';
				output[outputPos++] = '4';
				output[outputPos++] = ')';
				break;

			case 8309: 
			case 8325: 
			case 9316: 
			case 9465: 
			case 10106: 
			case 10116: 
			case 10126: 
			case 65301: 
				output[outputPos++] = '5';
				break;

			case 9356: 
				output[outputPos++] = '5';
				output[outputPos++] = '.';
				break;

			case 9336: 
				output[outputPos++] = '(';
				output[outputPos++] = '5';
				output[outputPos++] = ')';
				break;

			case 8310: 
			case 8326: 
			case 9317: 
			case 9466: 
			case 10107: 
			case 10117: 
			case 10127: 
			case 65302: 
				output[outputPos++] = '6';
				break;

			case 9357: 
				output[outputPos++] = '6';
				output[outputPos++] = '.';
				break;

			case 9337: 
				output[outputPos++] = '(';
				output[outputPos++] = '6';
				output[outputPos++] = ')';
				break;

			case 8311: 
			case 8327: 
			case 9318: 
			case 9467: 
			case 10108: 
			case 10118: 
			case 10128: 
			case 65303: 
				output[outputPos++] = '7';
				break;

			case 9358: 
				output[outputPos++] = '7';
				output[outputPos++] = '.';
				break;

			case 9338: 
				output[outputPos++] = '(';
				output[outputPos++] = '7';
				output[outputPos++] = ')';
				break;

			case 8312: 
			case 8328: 
			case 9319: 
			case 9468: 
			case 10109: 
			case 10119: 
			case 10129: 
			case 65304: 
				output[outputPos++] = '8';
				break;

			case 9359: 
				output[outputPos++] = '8';
				output[outputPos++] = '.';
				break;

			case 9339: 
				output[outputPos++] = '(';
				output[outputPos++] = '8';
				output[outputPos++] = ')';
				break;

			case 8313: 
			case 8329: 
			case 9320: 
			case 9469: 
			case 10110: 
			case 10120: 
			case 10130: 
			case 65305: 
				output[outputPos++] = '9';
				break;

			case 9360: 
				output[outputPos++] = '9';
				output[outputPos++] = '.';
				break;

			case 9340: 
				output[outputPos++] = '(';
				output[outputPos++] = '9';
				output[outputPos++] = ')';
				break;

			case 9321: 
			case 9470: 
			case 10111: 
			case 10121: 
			case 10131: 
				output[outputPos++] = '1';
				output[outputPos++] = '0';
				break;

			case 9361: 
				output[outputPos++] = '1';
				output[outputPos++] = '0';
				output[outputPos++] = '.';
				break;

			case 9341: 
				output[outputPos++] = '(';
				output[outputPos++] = '1';
				output[outputPos++] = '0';
				output[outputPos++] = ')';
				break;

			case 9322: 
			case 9451: 
				output[outputPos++] = '1';
				output[outputPos++] = '1';
				break;

			case 9362: 
				output[outputPos++] = '1';
				output[outputPos++] = '1';
				output[outputPos++] = '.';
				break;

			case 9342: 
				output[outputPos++] = '(';
				output[outputPos++] = '1';
				output[outputPos++] = '1';
				output[outputPos++] = ')';
				break;

			case 9323: 
			case 9452: 
				output[outputPos++] = '1';
				output[outputPos++] = '2';
				break;

			case 9363: 
				output[outputPos++] = '1';
				output[outputPos++] = '2';
				output[outputPos++] = '.';
				break;

			case 9343: 
				output[outputPos++] = '(';
				output[outputPos++] = '1';
				output[outputPos++] = '2';
				output[outputPos++] = ')';
				break;

			case 9324: 
			case 9453: 
				output[outputPos++] = '1';
				output[outputPos++] = '3';
				break;

			case 9364: 
				output[outputPos++] = '1';
				output[outputPos++] = '3';
				output[outputPos++] = '.';
				break;

			case 9344: 
				output[outputPos++] = '(';
				output[outputPos++] = '1';
				output[outputPos++] = '3';
				output[outputPos++] = ')';
				break;

			case 9325: 
			case 9454: 
				output[outputPos++] = '1';
				output[outputPos++] = '4';
				break;

			case 9365: 
				output[outputPos++] = '1';
				output[outputPos++] = '4';
				output[outputPos++] = '.';
				break;

			case 9345: 
				output[outputPos++] = '(';
				output[outputPos++] = '1';
				output[outputPos++] = '4';
				output[outputPos++] = ')';
				break;

			case 9326: 
			case 9455: 
				output[outputPos++] = '1';
				output[outputPos++] = '5';
				break;

			case 9366: 
				output[outputPos++] = '1';
				output[outputPos++] = '5';
				output[outputPos++] = '.';
				break;

			case 9346: 
				output[outputPos++] = '(';
				output[outputPos++] = '1';
				output[outputPos++] = '5';
				output[outputPos++] = ')';
				break;

			case 9327: 
			case 9456: 
				output[outputPos++] = '1';
				output[outputPos++] = '6';
				break;

			case 9367: 
				output[outputPos++] = '1';
				output[outputPos++] = '6';
				output[outputPos++] = '.';
				break;

			case 9347: 
				output[outputPos++] = '(';
				output[outputPos++] = '1';
				output[outputPos++] = '6';
				output[outputPos++] = ')';
				break;

			case 9328: 
			case 9457: 
				output[outputPos++] = '1';
				output[outputPos++] = '7';
				break;

			case 9368: 
				output[outputPos++] = '1';
				output[outputPos++] = '7';
				output[outputPos++] = '.';
				break;

			case 9348: 
				output[outputPos++] = '(';
				output[outputPos++] = '1';
				output[outputPos++] = '7';
				output[outputPos++] = ')';
				break;

			case 9329: 
			case 9458: 
				output[outputPos++] = '1';
				output[outputPos++] = '8';
				break;

			case 9369: 
				output[outputPos++] = '1';
				output[outputPos++] = '8';
				output[outputPos++] = '.';
				break;

			case 9349: 
				output[outputPos++] = '(';
				output[outputPos++] = '1';
				output[outputPos++] = '8';
				output[outputPos++] = ')';
				break;

			case 9330: 
			case 9459: 
				output[outputPos++] = '1';
				output[outputPos++] = '9';
				break;

			case 9370: 
				output[outputPos++] = '1';
				output[outputPos++] = '9';
				output[outputPos++] = '.';
				break;

			case 9350: 
				output[outputPos++] = '(';
				output[outputPos++] = '1';
				output[outputPos++] = '9';
				output[outputPos++] = ')';
				break;

			case 9331: 
			case 9460: 
				output[outputPos++] = '2';
				output[outputPos++] = '0';
				break;

			case 9371: 
				output[outputPos++] = '2';
				output[outputPos++] = '0';
				output[outputPos++] = '.';
				break;

			case 9351: 
				output[outputPos++] = '(';
				output[outputPos++] = '2';
				output[outputPos++] = '0';
				output[outputPos++] = ')';
				break;

			case 171: 
			case 187: 
			case 8220: 
			case 8221: 
			case 8222: 
			case 8243: 
			case 8246: 
			case 10077: 
			case 10078: 
			case 10094: 
			case 10095: 
			case 65282: 
				output[outputPos++] = '"';
				break;

			case 8216: 
			case 8217: 
			case 8218: 
			case 8219: 
			case 8242: 
			case 8245: 
			case 8249: 
			case 8250: 
			case 10075: 
			case 10076: 
			case 65287: 
				output[outputPos++] = '\'';
				break;

			case 8208: 
			case 8209: 
			case 8210: 
			case 8211: 
			case 8212: 
			case 8315: 
			case 8331: 
			case 65293: 
				output[outputPos++] = '-';
				break;

			case 8261: 
			case 10098: 
			case 65339: 
				output[outputPos++] = '[';
				break;

			case 8262: 
			case 10099: 
			case 65341: 
				output[outputPos++] = ']';
				break;

			case 8317: 
			case 8333: 
			case 10088: 
			case 10090: 
			case 65288: 
				output[outputPos++] = '(';
				break;

			case 11816: 
				output[outputPos++] = '(';
				output[outputPos++] = '(';
				break;

			case 8318: 
			case 8334: 
			case 10089: 
			case 10091: 
			case 65289: 
				output[outputPos++] = ')';
				break;

			case 11817: 
				output[outputPos++] = ')';
				output[outputPos++] = ')';
				break;

			case 10092: 
			case 10096: 
			case 65308: 
				output[outputPos++] = '<';
				break;

			case 10093: 
			case 10097: 
			case 65310: 
				output[outputPos++] = '>';
				break;

			case 10100: 
			case 65371: 
				output[outputPos++] = '{';
				break;

			case 10101: 
			case 65373: 
				output[outputPos++] = '}';
				break;

			case 8314: 
			case 8330: 
			case 65291: 
				output[outputPos++] = '+';
				break;

			case 8316: 
			case 8332: 
			case 65309: 
				output[outputPos++] = '=';
				break;

			case 65281: 
				output[outputPos++] = '!';
				break;

			case 8252: 
				output[outputPos++] = '!';
				output[outputPos++] = '!';
				break;

			case 8265: 
				output[outputPos++] = '!';
				output[outputPos++] = '?';
				break;

			case 65283: 
				output[outputPos++] = '#';
				break;

			case 65284: 
				output[outputPos++] = '$';
				break;

			case 8274: 
			case 65285: 
				output[outputPos++] = '%';
				break;

			case 65286: 
				output[outputPos++] = '&';
				break;

			case 8270: 
			case 65290: 
				output[outputPos++] = '*';
				break;

			case 65292: 
				output[outputPos++] = ',';
				break;

			case 65294: 
				output[outputPos++] = '.';
				break;

			case 8260: 
			case 65295: 
				output[outputPos++] = '/';
				break;

			case 65306: 
				output[outputPos++] = ':';
				break;

			case 8271: 
			case 65307: 
				output[outputPos++] = ';';
				break;

			case 65311: 
				output[outputPos++] = '?';
				break;

			case 8263: 
				output[outputPos++] = '?';
				output[outputPos++] = '?';
				break;

			case 8264: 
				output[outputPos++] = '?';
				output[outputPos++] = '!';
				break;

			case 65312: 
				output[outputPos++] = '@';
				break;

			case 65340: 
				output[outputPos++] = '\\';
				break;

			case 8248: 
			case 65342: 
				output[outputPos++] = '^';
				break;

			case 65343: 
				output[outputPos++] = '_';
				break;

			case 8275: 
			case 65374: 
				output[outputPos++] = '~';
				break;

			default:
				output[outputPos++] = c;
				break;
			}
		}

		return outputPos;
	}
}
