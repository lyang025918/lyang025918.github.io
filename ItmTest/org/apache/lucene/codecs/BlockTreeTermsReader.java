// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockTreeTermsReader.java

package org.apache.lucene.codecs;

import java.io.*;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;
import org.apache.lucene.util.automaton.*;
import org.apache.lucene.util.fst.*;

// Referenced classes of package org.apache.lucene.codecs:
//			FieldsProducer, PostingsReaderBase, CodecUtil, BlockTermState

public class BlockTreeTermsReader extends FieldsProducer
{
	public final class FieldReader extends Terms
	{
		private final class SegmentTermsEnum extends TermsEnum
		{
			private final class Frame
			{

				final int ord;
				boolean hasTerms;
				boolean hasTermsOrig;
				boolean isFloor;
				org.apache.lucene.util.fst.FST.Arc arc;
				long fp;
				long fpOrig;
				long fpEnd;
				byte suffixBytes[];
				final ByteArrayDataInput suffixesReader = new ByteArrayDataInput();
				byte statBytes[];
				final ByteArrayDataInput statsReader = new ByteArrayDataInput();
				byte floorData[];
				final ByteArrayDataInput floorDataReader = new ByteArrayDataInput();
				int prefix;
				int entCount;
				int nextEnt;
				boolean isLastInFloor;
				boolean isLeafBlock;
				long lastSubFP;
				int nextFloorLabel;
				int numFollowFloorBlocks;
				int metaDataUpto;
				final BlockTermState state;
				private int startBytePos;
				private int suffix;
				private long subCode;
				static final boolean $assertionsDisabled = !org/apache/lucene/codecs/BlockTreeTermsReader.desiredAssertionStatus();
				final SegmentTermsEnum this$2;

				public void setFloorData(ByteArrayDataInput in, BytesRef source)
				{
					int numBytes = source.length - (in.getPosition() - source.offset);
					if (numBytes > floorData.length)
						floorData = new byte[ArrayUtil.oversize(numBytes, 1)];
					System.arraycopy(source.bytes, source.offset + in.getPosition(), floorData, 0, numBytes);
					floorDataReader.reset(floorData, 0, numBytes);
					numFollowFloorBlocks = floorDataReader.readVInt();
					nextFloorLabel = floorDataReader.readByte() & 0xff;
				}

				public int getTermBlockOrd()
				{
					return isLeafBlock ? nextEnt : state.termBlockOrd;
				}

				void loadNextFloorBlock()
					throws IOException
				{
					if (!$assertionsDisabled && arc != null && !isFloor)
					{
						throw new AssertionError((new StringBuilder()).append("arc=").append(arc).append(" isFloor=").append(isFloor).toString());
					} else
					{
						fp = fpEnd;
						nextEnt = -1;
						loadBlock();
						return;
					}
				}

				void loadBlock()
					throws IOException
				{
					initIndexInput();
					if (nextEnt != -1)
						return;
					in.seek(fp);
					int code = in.readVInt();
					entCount = code >>> 1;
					if (!$assertionsDisabled && entCount <= 0)
						throw new AssertionError();
					isLastInFloor = (code & 1) != 0;
					if (!$assertionsDisabled && arc != null && !isLastInFloor && !isFloor)
						throw new AssertionError();
					code = in.readVInt();
					isLeafBlock = (code & 1) != 0;
					int numBytes = code >>> 1;
					if (suffixBytes.length < numBytes)
						suffixBytes = new byte[ArrayUtil.oversize(numBytes, 1)];
					in.readBytes(suffixBytes, 0, numBytes);
					suffixesReader.reset(suffixBytes, 0, numBytes);
					numBytes = in.readVInt();
					if (statBytes.length < numBytes)
						statBytes = new byte[ArrayUtil.oversize(numBytes, 1)];
					in.readBytes(statBytes, 0, numBytes);
					statsReader.reset(statBytes, 0, numBytes);
					metaDataUpto = 0;
					state.termBlockOrd = 0;
					nextEnt = 0;
					lastSubFP = -1L;
					postingsReader.readTermsBlock(in, fieldInfo, state);
					fpEnd = in.getFilePointer();
				}

				void rewind()
				{
					fp = fpOrig;
					nextEnt = -1;
					hasTerms = hasTermsOrig;
					if (isFloor)
					{
						floorDataReader.rewind();
						numFollowFloorBlocks = floorDataReader.readVInt();
						nextFloorLabel = floorDataReader.readByte() & 0xff;
					}
				}

				public boolean next()
				{
					return isLeafBlock ? nextLeaf() : nextNonLeaf();
				}

				public boolean nextLeaf()
				{
					if (!$assertionsDisabled && (nextEnt == -1 || nextEnt >= entCount))
						throw new AssertionError((new StringBuilder()).append("nextEnt=").append(nextEnt).append(" entCount=").append(entCount).append(" fp=").append(fp).toString());
					nextEnt++;
					suffix = suffixesReader.readVInt();
					startBytePos = suffixesReader.getPosition();
					term.length = prefix + suffix;
					if (term.bytes.length < term.length)
						term.grow(term.length);
					suffixesReader.readBytes(term.bytes, prefix, suffix);
					termExists = true;
					return false;
				}

				public boolean nextNonLeaf()
				{
					if (!$assertionsDisabled && (nextEnt == -1 || nextEnt >= entCount))
						throw new AssertionError((new StringBuilder()).append("nextEnt=").append(nextEnt).append(" entCount=").append(entCount).append(" fp=").append(fp).toString());
					nextEnt++;
					int code = suffixesReader.readVInt();
					suffix = code >>> 1;
					startBytePos = suffixesReader.getPosition();
					term.length = prefix + suffix;
					if (term.bytes.length < term.length)
						term.grow(term.length);
					suffixesReader.readBytes(term.bytes, prefix, suffix);
					if ((code & 1) == 0)
					{
						termExists = true;
						subCode = 0L;
						state.termBlockOrd++;
						return false;
					} else
					{
						termExists = false;
						subCode = suffixesReader.readVLong();
						lastSubFP = fp - subCode;
						return true;
					}
				}

				public void scanToFloorFrame(BytesRef target)
				{
					if (!isFloor || target.length <= prefix)
						return;
					int targetLabel = target.bytes[target.offset + prefix] & 0xff;
					if (targetLabel < nextFloorLabel)
						return;
					if (!$assertionsDisabled && numFollowFloorBlocks == 0)
						throw new AssertionError();
					long newFP = fpOrig;
					do
					{
						long code = floorDataReader.readVLong();
						newFP = fpOrig + (code >>> 1);
						hasTerms = (code & 1L) != 0L;
						isLastInFloor = numFollowFloorBlocks == 1;
						numFollowFloorBlocks--;
						if (isLastInFloor)
						{
							nextFloorLabel = 256;
							break;
						}
						nextFloorLabel = floorDataReader.readByte() & 0xff;
					} while (targetLabel >= nextFloorLabel);
					if (newFP != fp)
					{
						nextEnt = -1;
						fp = newFP;
					}
				}

				public void decodeMetaData()
					throws IOException
				{
					int limit = getTermBlockOrd();
					if (!$assertionsDisabled && limit <= 0)
						throw new AssertionError();
					for (state.termBlockOrd = metaDataUpto; metaDataUpto < limit; state.termBlockOrd++)
					{
						state.docFreq = statsReader.readVInt();
						if (fieldInfo.getIndexOptions() != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY)
							state.totalTermFreq = (long)state.docFreq + statsReader.readVLong();
						postingsReader.nextTerm(fieldInfo, state);
						metaDataUpto++;
					}

				}

				private boolean prefixMatches(BytesRef target)
				{
					for (int bytePos = 0; bytePos < prefix; bytePos++)
						if (target.bytes[target.offset + bytePos] != term.bytes[bytePos])
							return false;

					return true;
				}

				public void scanToSubBlock(long subFP)
				{
					if (!$assertionsDisabled && isLeafBlock)
						throw new AssertionError();
					if (lastSubFP == subFP)
						return;
					if (!$assertionsDisabled && subFP >= fp)
						throw new AssertionError((new StringBuilder()).append("fp=").append(fp).append(" subFP=").append(subFP).toString());
					long targetSubCode = fp - subFP;
					do
					{
						if (!$assertionsDisabled && nextEnt >= entCount)
							throw new AssertionError();
						nextEnt++;
						int code = suffixesReader.readVInt();
						suffixesReader.skipBytes(isLeafBlock ? code : code >>> 1);
						if ((code & 1) != 0)
						{
							long subCode = suffixesReader.readVLong();
							if (targetSubCode == subCode)
							{
								lastSubFP = subFP;
								return;
							}
						} else
						{
							state.termBlockOrd++;
						}
					} while (true);
				}

				public org.apache.lucene.index.TermsEnum.SeekStatus scanToTerm(BytesRef target, boolean exactOnly)
					throws IOException
				{
					return isLeafBlock ? scanToTermLeaf(target, exactOnly) : scanToTermNonLeaf(target, exactOnly);
				}

				public org.apache.lucene.index.TermsEnum.SeekStatus scanToTermLeaf(BytesRef target, boolean exactOnly)
					throws IOException
				{
					if (!$assertionsDisabled && nextEnt == -1)
						throw new AssertionError();
					termExists = true;
					subCode = 0L;
					if (nextEnt == entCount)
					{
						if (exactOnly)
							fillTerm();
						return org.apache.lucene.index.TermsEnum.SeekStatus.END;
					}
					if (!$assertionsDisabled && !prefixMatches(target))
						throw new AssertionError();
_L2:
					nextEnt++;
					suffix = suffixesReader.readVInt();
					int termLen = prefix + suffix;
					startBytePos = suffixesReader.getPosition();
					suffixesReader.skipBytes(suffix);
					int targetLimit = target.offset + (target.length >= termLen ? termLen : target.length);
					int targetPos = target.offset + prefix;
					int bytePos = startBytePos;
					do
					{
label0:
						{
							int cmp;
							boolean stop;
							if (targetPos < targetLimit)
							{
								cmp = (suffixBytes[bytePos++] & 0xff) - (target.bytes[targetPos++] & 0xff);
								stop = false;
							} else
							{
								if (!$assertionsDisabled && targetPos != targetLimit)
									throw new AssertionError();
								cmp = termLen - target.length;
								stop = true;
							}
							if (cmp < 0)
							{
								if (nextEnt == entCount)
								{
									if (exactOnly)
										fillTerm();
									break label0;
								}
								continue; /* Loop/switch isn't completed */
							}
							if (cmp > 0)
							{
								fillTerm();
								if (!exactOnly && !termExists)
								{
									currentFrame = pushFrame(null, currentFrame.lastSubFP, termLen);
									currentFrame.loadBlock();
									for (; currentFrame.next(); currentFrame.loadBlock())
										currentFrame = pushFrame(null, currentFrame.lastSubFP, term.length);

								}
								return org.apache.lucene.index.TermsEnum.SeekStatus.NOT_FOUND;
							}
							if (stop)
								if (!$assertionsDisabled && !termExists)
								{
									throw new AssertionError();
								} else
								{
									fillTerm();
									return org.apache.lucene.index.TermsEnum.SeekStatus.FOUND;
								}
						}
					} while (true);
					if (exactOnly)
						fillTerm();
					return org.apache.lucene.index.TermsEnum.SeekStatus.END;
					if (true) goto _L2; else goto _L1
_L1:
				}

				public org.apache.lucene.index.TermsEnum.SeekStatus scanToTermNonLeaf(BytesRef target, boolean exactOnly)
					throws IOException
				{
					if (!$assertionsDisabled && nextEnt == -1)
						throw new AssertionError();
					if (nextEnt == entCount)
					{
						if (exactOnly)
						{
							fillTerm();
							termExists = subCode == 0L;
						}
						return org.apache.lucene.index.TermsEnum.SeekStatus.END;
					}
					if (!$assertionsDisabled && !prefixMatches(target))
						throw new AssertionError();
_L2:
					nextEnt++;
					int code = suffixesReader.readVInt();
					suffix = code >>> 1;
					termExists = (code & 1) == 0;
					int termLen = prefix + suffix;
					startBytePos = suffixesReader.getPosition();
					suffixesReader.skipBytes(suffix);
					if (termExists)
					{
						state.termBlockOrd++;
						subCode = 0L;
					} else
					{
						subCode = suffixesReader.readVLong();
						lastSubFP = fp - subCode;
					}
					int targetLimit = target.offset + (target.length >= termLen ? termLen : target.length);
					int targetPos = target.offset + prefix;
					int bytePos = startBytePos;
					do
					{
label0:
						{
							int cmp;
							boolean stop;
							if (targetPos < targetLimit)
							{
								cmp = (suffixBytes[bytePos++] & 0xff) - (target.bytes[targetPos++] & 0xff);
								stop = false;
							} else
							{
								if (!$assertionsDisabled && targetPos != targetLimit)
									throw new AssertionError();
								cmp = termLen - target.length;
								stop = true;
							}
							if (cmp < 0)
							{
								if (nextEnt == entCount)
								{
									if (exactOnly)
										fillTerm();
									break label0;
								}
								continue; /* Loop/switch isn't completed */
							}
							if (cmp > 0)
							{
								fillTerm();
								if (!exactOnly && !termExists)
								{
									currentFrame = pushFrame(null, currentFrame.lastSubFP, termLen);
									currentFrame.loadBlock();
									for (; currentFrame.next(); currentFrame.loadBlock())
										currentFrame = pushFrame(null, currentFrame.lastSubFP, term.length);

								}
								return org.apache.lucene.index.TermsEnum.SeekStatus.NOT_FOUND;
							}
							if (stop)
								if (!$assertionsDisabled && !termExists)
								{
									throw new AssertionError();
								} else
								{
									fillTerm();
									return org.apache.lucene.index.TermsEnum.SeekStatus.FOUND;
								}
						}
					} while (true);
					if (exactOnly)
						fillTerm();
					return org.apache.lucene.index.TermsEnum.SeekStatus.END;
					if (true) goto _L2; else goto _L1
_L1:
				}

				private void fillTerm()
				{
					int termLength = prefix + suffix;
					term.length = prefix + suffix;
					if (term.bytes.length < termLength)
						term.grow(termLength);
					System.arraycopy(suffixBytes, startBytePos, term.bytes, prefix, suffix);
				}


				public Frame(int ord)
					throws IOException
				{
					this$2 = SegmentTermsEnum.this;
					super();
					suffixBytes = new byte[128];
					statBytes = new byte[64];
					floorData = new byte[32];
					this.ord = ord;
					state = postingsReader.newTermState();
					state.totalTermFreq = -1L;
				}
			}


			private IndexInput in;
			private Frame stack[];
			private final Frame staticFrame = new Frame(-1);
			private Frame currentFrame;
			private boolean termExists;
			private int targetBeforeCurrentLength;
			private final ByteArrayDataInput scratchReader = new ByteArrayDataInput();
			private int validIndexPrefix;
			private boolean eof;
			final BytesRef term = new BytesRef();
			private final org.apache.lucene.util.fst.FST.BytesReader fstReader;
			private org.apache.lucene.util.fst.FST.Arc arcs[];
			static final boolean $assertionsDisabled = !org/apache/lucene/codecs/BlockTreeTermsReader.desiredAssertionStatus();
			final FieldReader this$1;

			void initIndexInput()
			{
				if (in == null)
					in = 0.in.clone();
			}

			public Stats computeBlockStats()
				throws IOException
			{
				Stats stats = new Stats(segment, fieldInfo.name);
				if (index != null)
				{
					stats.indexNodeCount = index.getNodeCount();
					stats.indexArcCount = index.getArcCount();
					stats.indexNumBytes = index.sizeInBytes();
				}
				currentFrame = staticFrame;
				org.apache.lucene.util.fst.FST.Arc arc;
				if (index != null)
				{
					arc = index.getFirstArc(arcs[0]);
					if (!$assertionsDisabled && !arc.isFinal())
						throw new AssertionError();
				} else
				{
					arc = null;
				}
				currentFrame = pushFrame(arc, rootCode, 0);
				currentFrame.fpOrig = currentFrame.fp;
				currentFrame.loadBlock();
				validIndexPrefix = 0;
				stats.startBlock(currentFrame, !currentFrame.isLastInFloor);
				do
					if (currentFrame.nextEnt == currentFrame.entCount)
					{
						stats.endBlock(currentFrame);
						if (!currentFrame.isLastInFloor)
						{
							currentFrame.loadNextFloorBlock();
							stats.startBlock(currentFrame, true);
							continue;
						}
						if (currentFrame.ord == 0)
							break;
						long lastFP = currentFrame.fpOrig;
						currentFrame = stack[currentFrame.ord - 1];
						if (!$assertionsDisabled && lastFP != currentFrame.lastSubFP)
							throw new AssertionError();
					} else
					{
						for (; currentFrame.next(); stats.startBlock(currentFrame, !currentFrame.isLastInFloor))
						{
							currentFrame = pushFrame(null, currentFrame.lastSubFP, term.length);
							currentFrame.fpOrig = currentFrame.fp;
							currentFrame.isFloor = false;
							currentFrame.loadBlock();
						}

						stats.term(term);
					}
				while (true);
				stats.finish();
				currentFrame = staticFrame;
				if (index != null)
				{
					arc = index.getFirstArc(arcs[0]);
					if (!$assertionsDisabled && !arc.isFinal())
						throw new AssertionError();
				} else
				{
					arc = null;
				}
				currentFrame = pushFrame(arc, rootCode, 0);
				currentFrame.rewind();
				currentFrame.loadBlock();
				validIndexPrefix = 0;
				term.length = 0;
				return stats;
			}

			private Frame getFrame(int ord)
				throws IOException
			{
				if (ord >= stack.length)
				{
					Frame next[] = new Frame[ArrayUtil.oversize(1 + ord, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
					System.arraycopy(stack, 0, next, 0, stack.length);
					for (int stackOrd = stack.length; stackOrd < next.length; stackOrd++)
						next[stackOrd] = new Frame(stackOrd);

					stack = next;
				}
				if (!$assertionsDisabled && stack[ord].ord != ord)
					throw new AssertionError();
				else
					return stack[ord];
			}

			private org.apache.lucene.util.fst.FST.Arc getArc(int ord)
			{
				if (ord >= arcs.length)
				{
					org.apache.lucene.util.fst.FST.Arc next[] = new org.apache.lucene.util.fst.FST.Arc[ArrayUtil.oversize(1 + ord, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
					System.arraycopy(arcs, 0, next, 0, arcs.length);
					for (int arcOrd = arcs.length; arcOrd < next.length; arcOrd++)
						next[arcOrd] = new org.apache.lucene.util.fst.FST.Arc();

					arcs = next;
				}
				return arcs[ord];
			}

			public Comparator getComparator()
			{
				return BytesRef.getUTF8SortedAsUnicodeComparator();
			}

			Frame pushFrame(org.apache.lucene.util.fst.FST.Arc arc, BytesRef frameData, int length)
				throws IOException
			{
				scratchReader.reset(frameData.bytes, frameData.offset, frameData.length);
				long code = scratchReader.readVLong();
				long fpSeek = code >>> 2;
				Frame f = getFrame(1 + currentFrame.ord);
				f.hasTerms = (code & 2L) != 0L;
				f.hasTermsOrig = f.hasTerms;
				f.isFloor = (code & 1L) != 0L;
				if (f.isFloor)
					f.setFloorData(scratchReader, frameData);
				pushFrame(arc, fpSeek, length);
				return f;
			}

			Frame pushFrame(org.apache.lucene.util.fst.FST.Arc arc, long fp, int length)
				throws IOException
			{
				Frame f = getFrame(1 + currentFrame.ord);
				f.arc = arc;
				if (f.fpOrig == fp && f.nextEnt != -1)
				{
					if (f.prefix > targetBeforeCurrentLength)
						f.rewind();
					if (!$assertionsDisabled && length != f.prefix)
						throw new AssertionError();
				} else
				{
					f.nextEnt = -1;
					f.prefix = length;
					f.state.termBlockOrd = 0;
					f.fpOrig = f.fp = fp;
					f.lastSubFP = -1L;
				}
				return f;
			}

			private boolean clearEOF()
			{
				eof = false;
				return true;
			}

			private boolean setEOF()
			{
				eof = true;
				return true;
			}

			public boolean seekExact(BytesRef target, boolean useCache)
				throws IOException
			{
				if (index == null)
					throw new IllegalStateException("terms index was not loaded");
				if (term.bytes.length <= target.length)
					term.bytes = ArrayUtil.grow(term.bytes, 1 + target.length);
				if (!$assertionsDisabled && !clearEOF())
					throw new AssertionError();
				targetBeforeCurrentLength = currentFrame.ord;
				org.apache.lucene.util.fst.FST.Arc arc;
				int targetUpto;
				BytesRef output;
				if (currentFrame != staticFrame)
				{
					arc = arcs[0];
					if (!$assertionsDisabled && !arc.isFinal())
						throw new AssertionError();
					output = (BytesRef)arc.output;
					targetUpto = 0;
					Frame lastFrame = stack[0];
					if (!$assertionsDisabled && validIndexPrefix > term.length)
						throw new AssertionError();
					int targetLimit = Math.min(target.length, validIndexPrefix);
					int cmp = 0;
					for (; targetUpto < targetLimit; targetUpto++)
					{
						cmp = (term.bytes[targetUpto] & 0xff) - (target.bytes[target.offset + targetUpto] & 0xff);
						if (cmp != 0)
							break;
						arc = arcs[1 + targetUpto];
						if (!$assertionsDisabled && arc.label != (target.bytes[target.offset + targetUpto] & 0xff))
							throw new AssertionError((new StringBuilder()).append("arc.label=").append((char)arc.label).append(" targetLabel=").append((char)(target.bytes[target.offset + targetUpto] & 0xff)).toString());
						if (arc.output != NO_OUTPUT)
							output = (BytesRef)fstOutputs.add(output, arc.output);
						if (arc.isFinal())
							lastFrame = stack[1 + lastFrame.ord];
					}

					if (cmp == 0)
					{
						int targetUptoMid = targetUpto;
						int targetLimit2 = Math.min(target.length, term.length);
						do
						{
							if (targetUpto >= targetLimit2)
								break;
							cmp = (term.bytes[targetUpto] & 0xff) - (target.bytes[target.offset + targetUpto] & 0xff);
							if (cmp != 0)
								break;
							targetUpto++;
						} while (true);
						if (cmp == 0)
							cmp = term.length - target.length;
						targetUpto = targetUptoMid;
					}
					if (cmp < 0)
						currentFrame = lastFrame;
					else
					if (cmp > 0)
					{
						targetBeforeCurrentLength = 0;
						currentFrame = lastFrame;
						currentFrame.rewind();
					} else
					{
						if (!$assertionsDisabled && term.length != target.length)
							throw new AssertionError();
						if (termExists)
							return true;
					}
				} else
				{
					targetBeforeCurrentLength = -1;
					arc = index.getFirstArc(arcs[0]);
					if (!$assertionsDisabled && !arc.isFinal())
						throw new AssertionError();
					if (!$assertionsDisabled && arc.output == null)
						throw new AssertionError();
					output = (BytesRef)arc.output;
					currentFrame = staticFrame;
					targetUpto = 0;
					currentFrame = pushFrame(arc, (BytesRef)fstOutputs.add(output, arc.nextFinalOutput), 0);
				}
				do
				{
					if (targetUpto >= target.length)
						break;
					int targetLabel = target.bytes[target.offset + targetUpto] & 0xff;
					org.apache.lucene.util.fst.FST.Arc nextArc = index.findTargetArc(targetLabel, arc, getArc(1 + targetUpto), fstReader);
					if (nextArc == null)
					{
						validIndexPrefix = currentFrame.prefix;
						currentFrame.scanToFloorFrame(target);
						if (!currentFrame.hasTerms)
						{
							termExists = false;
							term.bytes[targetUpto] = (byte)targetLabel;
							term.length = 1 + targetUpto;
							return false;
						}
						currentFrame.loadBlock();
						org.apache.lucene.index.TermsEnum.SeekStatus result = currentFrame.scanToTerm(target, true);
						return result == org.apache.lucene.index.TermsEnum.SeekStatus.FOUND;
					}
					arc = nextArc;
					term.bytes[targetUpto] = (byte)targetLabel;
					if (!$assertionsDisabled && arc.output == null)
						throw new AssertionError();
					if (arc.output != NO_OUTPUT)
						output = (BytesRef)fstOutputs.add(output, arc.output);
					targetUpto++;
					if (arc.isFinal())
						currentFrame = pushFrame(arc, (BytesRef)fstOutputs.add(output, arc.nextFinalOutput), targetUpto);
				} while (true);
				validIndexPrefix = currentFrame.prefix;
				currentFrame.scanToFloorFrame(target);
				if (!currentFrame.hasTerms)
				{
					termExists = false;
					term.length = targetUpto;
					return false;
				}
				currentFrame.loadBlock();
				org.apache.lucene.index.TermsEnum.SeekStatus result = currentFrame.scanToTerm(target, true);
				return result == org.apache.lucene.index.TermsEnum.SeekStatus.FOUND;
			}

			public org.apache.lucene.index.TermsEnum.SeekStatus seekCeil(BytesRef target, boolean useCache)
				throws IOException
			{
				if (index == null)
					throw new IllegalStateException("terms index was not loaded");
				if (term.bytes.length <= target.length)
					term.bytes = ArrayUtil.grow(term.bytes, 1 + target.length);
				if (!$assertionsDisabled && !clearEOF())
					throw new AssertionError();
				targetBeforeCurrentLength = currentFrame.ord;
				org.apache.lucene.util.fst.FST.Arc arc;
				int targetUpto;
				BytesRef output;
				if (currentFrame != staticFrame)
				{
					arc = arcs[0];
					if (!$assertionsDisabled && !arc.isFinal())
						throw new AssertionError();
					output = (BytesRef)arc.output;
					targetUpto = 0;
					Frame lastFrame = stack[0];
					if (!$assertionsDisabled && validIndexPrefix > term.length)
						throw new AssertionError();
					int targetLimit = Math.min(target.length, validIndexPrefix);
					int cmp = 0;
					for (; targetUpto < targetLimit; targetUpto++)
					{
						cmp = (term.bytes[targetUpto] & 0xff) - (target.bytes[target.offset + targetUpto] & 0xff);
						if (cmp != 0)
							break;
						arc = arcs[1 + targetUpto];
						if (!$assertionsDisabled && arc.label != (target.bytes[target.offset + targetUpto] & 0xff))
							throw new AssertionError((new StringBuilder()).append("arc.label=").append((char)arc.label).append(" targetLabel=").append((char)(target.bytes[target.offset + targetUpto] & 0xff)).toString());
						if (arc.output != NO_OUTPUT)
							output = (BytesRef)fstOutputs.add(output, arc.output);
						if (arc.isFinal())
							lastFrame = stack[1 + lastFrame.ord];
					}

					if (cmp == 0)
					{
						int targetUptoMid = targetUpto;
						int targetLimit2 = Math.min(target.length, term.length);
						do
						{
							if (targetUpto >= targetLimit2)
								break;
							cmp = (term.bytes[targetUpto] & 0xff) - (target.bytes[target.offset + targetUpto] & 0xff);
							if (cmp != 0)
								break;
							targetUpto++;
						} while (true);
						if (cmp == 0)
							cmp = term.length - target.length;
						targetUpto = targetUptoMid;
					}
					if (cmp < 0)
						currentFrame = lastFrame;
					else
					if (cmp > 0)
					{
						targetBeforeCurrentLength = 0;
						currentFrame = lastFrame;
						currentFrame.rewind();
					} else
					{
						if (!$assertionsDisabled && term.length != target.length)
							throw new AssertionError();
						if (termExists)
							return org.apache.lucene.index.TermsEnum.SeekStatus.FOUND;
					}
				} else
				{
					targetBeforeCurrentLength = -1;
					arc = index.getFirstArc(arcs[0]);
					if (!$assertionsDisabled && !arc.isFinal())
						throw new AssertionError();
					if (!$assertionsDisabled && arc.output == null)
						throw new AssertionError();
					output = (BytesRef)arc.output;
					currentFrame = staticFrame;
					targetUpto = 0;
					currentFrame = pushFrame(arc, (BytesRef)fstOutputs.add(output, arc.nextFinalOutput), 0);
				}
				do
				{
					if (targetUpto >= target.length)
						break;
					int targetLabel = target.bytes[target.offset + targetUpto] & 0xff;
					org.apache.lucene.util.fst.FST.Arc nextArc = index.findTargetArc(targetLabel, arc, getArc(1 + targetUpto), fstReader);
					if (nextArc == null)
					{
						validIndexPrefix = currentFrame.prefix;
						currentFrame.scanToFloorFrame(target);
						currentFrame.loadBlock();
						org.apache.lucene.index.TermsEnum.SeekStatus result = currentFrame.scanToTerm(target, false);
						if (result == org.apache.lucene.index.TermsEnum.SeekStatus.END)
						{
							term.copyBytes(target);
							termExists = false;
							if (next() != null)
								return org.apache.lucene.index.TermsEnum.SeekStatus.NOT_FOUND;
							else
								return org.apache.lucene.index.TermsEnum.SeekStatus.END;
						} else
						{
							return result;
						}
					}
					term.bytes[targetUpto] = (byte)targetLabel;
					arc = nextArc;
					if (!$assertionsDisabled && arc.output == null)
						throw new AssertionError();
					if (arc.output != NO_OUTPUT)
						output = (BytesRef)fstOutputs.add(output, arc.output);
					targetUpto++;
					if (arc.isFinal())
						currentFrame = pushFrame(arc, (BytesRef)fstOutputs.add(output, arc.nextFinalOutput), targetUpto);
				} while (true);
				validIndexPrefix = currentFrame.prefix;
				currentFrame.scanToFloorFrame(target);
				currentFrame.loadBlock();
				org.apache.lucene.index.TermsEnum.SeekStatus result = currentFrame.scanToTerm(target, false);
				if (result == org.apache.lucene.index.TermsEnum.SeekStatus.END)
				{
					term.copyBytes(target);
					termExists = false;
					if (next() != null)
						return org.apache.lucene.index.TermsEnum.SeekStatus.NOT_FOUND;
					else
						return org.apache.lucene.index.TermsEnum.SeekStatus.END;
				} else
				{
					return result;
				}
			}

			private void printSeekState(PrintStream out)
				throws IOException
			{
				if (currentFrame == staticFrame)
				{
					out.println("  no prior seek");
				} else
				{
					out.println("  prior seek state:");
					int ord = 0;
					boolean isSeekFrame = true;
					do
					{
						Frame f = getFrame(ord);
						if (!$assertionsDisabled && f == null)
							throw new AssertionError();
						BytesRef prefix = new BytesRef(term.bytes, 0, f.prefix);
						if (f.nextEnt == -1)
							out.println((new StringBuilder()).append("    frame ").append(isSeekFrame ? "(seek)" : "(next)").append(" ord=").append(ord).append(" fp=").append(f.fp).append(f.isFloor ? (new StringBuilder()).append(" (fpOrig=").append(f.fpOrig).append(")").toString() : "").append(" prefixLen=").append(f.prefix).append(" prefix=").append(prefix).append(f.nextEnt != -1 ? (new StringBuilder()).append(" (of ").append(f.entCount).append(")").toString() : "").append(" hasTerms=").append(f.hasTerms).append(" isFloor=").append(f.isFloor).append(" code=").append((f.fp << 2) + (long)(f.hasTerms ? 2 : 0) + (long)(f.isFloor ? 1 : 0)).append(" isLastInFloor=").append(f.isLastInFloor).append(" mdUpto=").append(f.metaDataUpto).append(" tbOrd=").append(f.getTermBlockOrd()).toString());
						else
							out.println((new StringBuilder()).append("    frame ").append(isSeekFrame ? "(seek, loaded)" : "(next, loaded)").append(" ord=").append(ord).append(" fp=").append(f.fp).append(f.isFloor ? (new StringBuilder()).append(" (fpOrig=").append(f.fpOrig).append(")").toString() : "").append(" prefixLen=").append(f.prefix).append(" prefix=").append(prefix).append(" nextEnt=").append(f.nextEnt).append(f.nextEnt != -1 ? (new StringBuilder()).append(" (of ").append(f.entCount).append(")").toString() : "").append(" hasTerms=").append(f.hasTerms).append(" isFloor=").append(f.isFloor).append(" code=").append((f.fp << 2) + (long)(f.hasTerms ? 2 : 0) + (long)(f.isFloor ? 1 : 0)).append(" lastSubFP=").append(f.lastSubFP).append(" isLastInFloor=").append(f.isLastInFloor).append(" mdUpto=").append(f.metaDataUpto).append(" tbOrd=").append(f.getTermBlockOrd()).toString());
						if (index != null)
						{
							if (!$assertionsDisabled && isSeekFrame && f.arc == null)
								throw new AssertionError((new StringBuilder()).append("isSeekFrame=").append(isSeekFrame).append(" f.arc=").append(f.arc).toString());
							if (f.prefix > 0 && isSeekFrame && f.arc.label != (term.bytes[f.prefix - 1] & 0xff))
							{
								out.println((new StringBuilder()).append("      broken seek state: arc.label=").append((char)f.arc.label).append(" vs term byte=").append((char)(term.bytes[f.prefix - 1] & 0xff)).toString());
								throw new RuntimeException("seek state is broken");
							}
							BytesRef output = (BytesRef)Util.get(index, prefix);
							if (output == null)
							{
								out.println("      broken seek state: prefix is not final in index");
								throw new RuntimeException("seek state is broken");
							}
							if (isSeekFrame && !f.isFloor)
							{
								ByteArrayDataInput reader = new ByteArrayDataInput(output.bytes, output.offset, output.length);
								long codeOrig = reader.readVLong();
								long code = f.fp << 2 | (long)(f.hasTerms ? 2 : 0) | (long)(f.isFloor ? 1 : 0);
								if (codeOrig != code)
								{
									out.println((new StringBuilder()).append("      broken seek state: output code=").append(codeOrig).append(" doesn't match frame code=").append(code).toString());
									throw new RuntimeException("seek state is broken");
								}
							}
						}
						if (f == currentFrame)
							break;
						if (f.prefix == validIndexPrefix)
							isSeekFrame = false;
						ord++;
					} while (true);
				}
			}

			public BytesRef next()
				throws IOException
			{
				if (in == null)
				{
					org.apache.lucene.util.fst.FST.Arc arc;
					if (index != null)
					{
						arc = index.getFirstArc(arcs[0]);
						if (!$assertionsDisabled && !arc.isFinal())
							throw new AssertionError();
					} else
					{
						arc = null;
					}
					currentFrame = pushFrame(arc, rootCode, 0);
					currentFrame.loadBlock();
				}
				targetBeforeCurrentLength = currentFrame.ord;
				if (!$assertionsDisabled && eof)
					throw new AssertionError();
				if (currentFrame == staticFrame)
				{
					boolean result = seekExact(term, false);
					if (!$assertionsDisabled && !result)
						throw new AssertionError();
				}
				while (currentFrame.nextEnt == currentFrame.entCount) 
					if (!currentFrame.isLastInFloor)
					{
						currentFrame.loadNextFloorBlock();
					} else
					{
						if (currentFrame.ord == 0)
							if (!$assertionsDisabled && !setEOF())
							{
								throw new AssertionError();
							} else
							{
								term.length = 0;
								validIndexPrefix = 0;
								currentFrame.rewind();
								termExists = false;
								return null;
							}
						long lastFP = currentFrame.fpOrig;
						currentFrame = stack[currentFrame.ord - 1];
						if (currentFrame.nextEnt == -1 || currentFrame.lastSubFP != lastFP)
						{
							currentFrame.scanToFloorFrame(term);
							currentFrame.loadBlock();
							currentFrame.scanToSubBlock(lastFP);
						}
						validIndexPrefix = Math.min(validIndexPrefix, currentFrame.prefix);
					}
				for (; currentFrame.next(); currentFrame.loadBlock())
				{
					currentFrame = pushFrame(null, currentFrame.lastSubFP, term.length);
					currentFrame.isFloor = false;
				}

				return term;
			}

			public BytesRef term()
			{
				if (!$assertionsDisabled && eof)
					throw new AssertionError();
				else
					return term;
			}

			public int docFreq()
				throws IOException
			{
				if (!$assertionsDisabled && eof)
				{
					throw new AssertionError();
				} else
				{
					currentFrame.decodeMetaData();
					return currentFrame.state.docFreq;
				}
			}

			public long totalTermFreq()
				throws IOException
			{
				if (!$assertionsDisabled && eof)
				{
					throw new AssertionError();
				} else
				{
					currentFrame.decodeMetaData();
					return currentFrame.state.totalTermFreq;
				}
			}

			public DocsEnum docs(Bits skipDocs, DocsEnum reuse, int flags)
				throws IOException
			{
				if (!$assertionsDisabled && eof)
				{
					throw new AssertionError();
				} else
				{
					currentFrame.decodeMetaData();
					return postingsReader.docs(fieldInfo, currentFrame.state, skipDocs, reuse, flags);
				}
			}

			public DocsAndPositionsEnum docsAndPositions(Bits skipDocs, DocsAndPositionsEnum reuse, int flags)
				throws IOException
			{
				if (fieldInfo.getIndexOptions().compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) < 0)
					return null;
				if (!$assertionsDisabled && eof)
				{
					throw new AssertionError();
				} else
				{
					currentFrame.decodeMetaData();
					return postingsReader.docsAndPositions(fieldInfo, currentFrame.state, skipDocs, reuse, flags);
				}
			}

			public void seekExact(BytesRef target, TermState otherState)
			{
				if (!$assertionsDisabled && !clearEOF())
					throw new AssertionError();
				if (target.compareTo(term) != 0 || !termExists)
				{
					if (!$assertionsDisabled && (otherState == null || !(otherState instanceof BlockTermState)))
						throw new AssertionError();
					currentFrame = staticFrame;
					currentFrame.state.copyFrom(otherState);
					term.copyBytes(target);
					currentFrame.metaDataUpto = currentFrame.getTermBlockOrd();
					if (!$assertionsDisabled && currentFrame.metaDataUpto <= 0)
						throw new AssertionError();
					validIndexPrefix = 0;
				}
			}

			public TermState termState()
				throws IOException
			{
				if (!$assertionsDisabled && eof)
				{
					throw new AssertionError();
				} else
				{
					currentFrame.decodeMetaData();
					TermState ts = currentFrame.state.clone();
					return ts;
				}
			}

			public void seekExact(long ord)
			{
				throw new UnsupportedOperationException();
			}

			public long ord()
			{
				throw new UnsupportedOperationException();
			}







			public SegmentTermsEnum()
				throws IOException
			{
				this$1 = FieldReader.this;
				super();
				arcs = new org.apache.lucene.util.fst.FST.Arc[1];
				stack = new Frame[0];
				if (index == null)
					fstReader = null;
				else
					fstReader = index.getBytesReader(0);
				for (int arcIdx = 0; arcIdx < arcs.length; arcIdx++)
					arcs[arcIdx] = new org.apache.lucene.util.fst.FST.Arc();

				currentFrame = staticFrame;
				org.apache.lucene.util.fst.FST.Arc arc;
				if (index != null)
				{
					arc = index.getFirstArc(arcs[0]);
					if (!$assertionsDisabled && !arc.isFinal())
						throw new AssertionError();
				} else
				{
					arc = null;
				}
				currentFrame = staticFrame;
				validIndexPrefix = 0;
			}
		}

		private final class IntersectEnum extends TermsEnum
		{
			private final class Frame
			{

				final int ord;
				long fp;
				long fpOrig;
				long fpEnd;
				long lastSubFP;
				int state;
				int metaDataUpto;
				byte suffixBytes[];
				final ByteArrayDataInput suffixesReader = new ByteArrayDataInput();
				byte statBytes[];
				final ByteArrayDataInput statsReader = new ByteArrayDataInput();
				byte floorData[];
				final ByteArrayDataInput floorDataReader = new ByteArrayDataInput();
				int prefix;
				int entCount;
				int nextEnt;
				boolean isLastInFloor;
				boolean isLeafBlock;
				int numFollowFloorBlocks;
				int nextFloorLabel;
				Transition transitions[];
				int curTransitionMax;
				int transitionIndex;
				org.apache.lucene.util.fst.FST.Arc arc;
				final BlockTermState termState;
				BytesRef outputPrefix;
				private int startBytePos;
				private int suffix;
				static final boolean $assertionsDisabled = !org/apache/lucene/codecs/BlockTreeTermsReader.desiredAssertionStatus();
				final IntersectEnum this$2;

				void loadNextFloorBlock()
					throws IOException
				{
					if (!$assertionsDisabled && numFollowFloorBlocks <= 0)
						throw new AssertionError();
					do
					{
						fp = fpOrig + (floorDataReader.readVLong() >>> 1);
						numFollowFloorBlocks--;
						if (numFollowFloorBlocks != 0)
							nextFloorLabel = floorDataReader.readByte() & 0xff;
						else
							nextFloorLabel = 256;
					} while (numFollowFloorBlocks != 0 && nextFloorLabel <= transitions[transitionIndex].getMin());
					load(null);
				}

				public void setState(int state)
				{
					this.state = state;
					transitionIndex = 0;
					transitions = compiledAutomaton.sortedTransitions[state];
					if (transitions.length != 0)
						curTransitionMax = transitions[0].getMax();
					else
						curTransitionMax = -1;
				}

				void load(BytesRef frameIndexData)
					throws IOException
				{
					int code;
					if (frameIndexData != null && transitions.length != 0)
					{
						if (floorData.length < frameIndexData.length)
							floorData = new byte[ArrayUtil.oversize(frameIndexData.length, 1)];
						System.arraycopy(frameIndexData.bytes, frameIndexData.offset, floorData, 0, frameIndexData.length);
						floorDataReader.reset(floorData, 0, frameIndexData.length);
						code = floorDataReader.readVLong();
						if ((code & 1L) != 0L)
						{
							numFollowFloorBlocks = floorDataReader.readVInt();
							nextFloorLabel = floorDataReader.readByte() & 0xff;
							if (!runAutomaton.isAccept(state))
								while (numFollowFloorBlocks != 0 && nextFloorLabel <= transitions[0].getMin()) 
								{
									fp = fpOrig + (floorDataReader.readVLong() >>> 1);
									numFollowFloorBlocks--;
									if (numFollowFloorBlocks != 0)
										nextFloorLabel = floorDataReader.readByte() & 0xff;
									else
										nextFloorLabel = 256;
								}
						}
					}
					in.seek(fp);
					code = in.readVInt();
					entCount = code >>> 1;
					if (!$assertionsDisabled && entCount <= 0)
						throw new AssertionError();
					isLastInFloor = (code & 1) != 0;
					code = in.readVInt();
					isLeafBlock = (code & 1) != 0;
					int numBytes = code >>> 1;
					if (suffixBytes.length < numBytes)
						suffixBytes = new byte[ArrayUtil.oversize(numBytes, 1)];
					in.readBytes(suffixBytes, 0, numBytes);
					suffixesReader.reset(suffixBytes, 0, numBytes);
					numBytes = in.readVInt();
					if (statBytes.length < numBytes)
						statBytes = new byte[ArrayUtil.oversize(numBytes, 1)];
					in.readBytes(statBytes, 0, numBytes);
					statsReader.reset(statBytes, 0, numBytes);
					metaDataUpto = 0;
					termState.termBlockOrd = 0;
					nextEnt = 0;
					postingsReader.readTermsBlock(in, fieldInfo, termState);
					if (!isLastInFloor)
						fpEnd = in.getFilePointer();
				}

				public boolean next()
				{
					return isLeafBlock ? nextLeaf() : nextNonLeaf();
				}

				public boolean nextLeaf()
				{
					if (!$assertionsDisabled && (nextEnt == -1 || nextEnt >= entCount))
					{
						throw new AssertionError((new StringBuilder()).append("nextEnt=").append(nextEnt).append(" entCount=").append(entCount).append(" fp=").append(fp).toString());
					} else
					{
						nextEnt++;
						suffix = suffixesReader.readVInt();
						startBytePos = suffixesReader.getPosition();
						suffixesReader.skipBytes(suffix);
						return false;
					}
				}

				public boolean nextNonLeaf()
				{
					if (!$assertionsDisabled && (nextEnt == -1 || nextEnt >= entCount))
						throw new AssertionError((new StringBuilder()).append("nextEnt=").append(nextEnt).append(" entCount=").append(entCount).append(" fp=").append(fp).toString());
					nextEnt++;
					int code = suffixesReader.readVInt();
					suffix = code >>> 1;
					startBytePos = suffixesReader.getPosition();
					suffixesReader.skipBytes(suffix);
					if ((code & 1) == 0)
					{
						termState.termBlockOrd++;
						return false;
					} else
					{
						lastSubFP = fp - suffixesReader.readVLong();
						return true;
					}
				}

				public int getTermBlockOrd()
				{
					return isLeafBlock ? nextEnt : termState.termBlockOrd;
				}

				public void decodeMetaData()
					throws IOException
				{
					int limit = getTermBlockOrd();
					if (!$assertionsDisabled && limit <= 0)
						throw new AssertionError();
					for (termState.termBlockOrd = metaDataUpto; metaDataUpto < limit; termState.termBlockOrd++)
					{
						termState.docFreq = statsReader.readVInt();
						if (fieldInfo.getIndexOptions() != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY)
							termState.totalTermFreq = (long)termState.docFreq + statsReader.readVLong();
						postingsReader.nextTerm(fieldInfo, termState);
						metaDataUpto++;
					}

				}






				public Frame(int ord)
					throws IOException
				{
					this$2 = IntersectEnum.this;
					super();
					suffixBytes = new byte[128];
					statBytes = new byte[64];
					floorData = new byte[32];
					this.ord = ord;
					termState = postingsReader.newTermState();
					termState.totalTermFreq = -1L;
				}
			}


			private final IndexInput in;
			private Frame stack[];
			private org.apache.lucene.util.fst.FST.Arc arcs[];
			private final RunAutomaton runAutomaton;
			private final CompiledAutomaton compiledAutomaton;
			private Frame currentFrame;
			private final BytesRef term = new BytesRef();
			private final org.apache.lucene.util.fst.FST.BytesReader fstReader;
			private BytesRef savedStartTerm;
			static final boolean $assertionsDisabled = !org/apache/lucene/codecs/BlockTreeTermsReader.desiredAssertionStatus();
			final FieldReader this$1;

			private boolean setSavedStartTerm(BytesRef startTerm)
			{
				savedStartTerm = startTerm != null ? BytesRef.deepCopyOf(startTerm) : null;
				return true;
			}

			public TermState termState()
				throws IOException
			{
				currentFrame.decodeMetaData();
				return currentFrame.termState.clone();
			}

			private Frame getFrame(int ord)
				throws IOException
			{
				if (ord >= stack.length)
				{
					Frame next[] = new Frame[ArrayUtil.oversize(1 + ord, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
					System.arraycopy(stack, 0, next, 0, stack.length);
					for (int stackOrd = stack.length; stackOrd < next.length; stackOrd++)
						next[stackOrd] = new Frame(stackOrd);

					stack = next;
				}
				if (!$assertionsDisabled && stack[ord].ord != ord)
					throw new AssertionError();
				else
					return stack[ord];
			}

			private org.apache.lucene.util.fst.FST.Arc getArc(int ord)
			{
				if (ord >= arcs.length)
				{
					org.apache.lucene.util.fst.FST.Arc next[] = new org.apache.lucene.util.fst.FST.Arc[ArrayUtil.oversize(1 + ord, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
					System.arraycopy(arcs, 0, next, 0, arcs.length);
					for (int arcOrd = arcs.length; arcOrd < next.length; arcOrd++)
						next[arcOrd] = new org.apache.lucene.util.fst.FST.Arc();

					arcs = next;
				}
				return arcs[ord];
			}

			private Frame pushFrame(int state)
				throws IOException
			{
				Frame f = getFrame(currentFrame != null ? 1 + currentFrame.ord : 0);
				f.fp = f.fpOrig = currentFrame.lastSubFP;
				f.prefix = currentFrame.prefix + currentFrame.suffix;
				f.setState(state);
				org.apache.lucene.util.fst.FST.Arc arc = currentFrame.arc;
				int idx = currentFrame.prefix;
				if (!$assertionsDisabled && currentFrame.suffix <= 0)
					throw new AssertionError();
				BytesRef output = currentFrame.outputPrefix;
				for (; idx < f.prefix; idx++)
				{
					int target = term.bytes[idx] & 0xff;
					arc = index.findTargetArc(target, arc, getArc(1 + idx), fstReader);
					if (!$assertionsDisabled && arc == null)
						throw new AssertionError();
					output = (BytesRef)fstOutputs.add(output, arc.output);
				}

				f.arc = arc;
				f.outputPrefix = output;
				if (!$assertionsDisabled && !arc.isFinal())
				{
					throw new AssertionError();
				} else
				{
					f.load((BytesRef)fstOutputs.add(output, arc.nextFinalOutput));
					return f;
				}
			}

			public BytesRef term()
			{
				return term;
			}

			public int docFreq()
				throws IOException
			{
				currentFrame.decodeMetaData();
				return currentFrame.termState.docFreq;
			}

			public long totalTermFreq()
				throws IOException
			{
				currentFrame.decodeMetaData();
				return currentFrame.termState.totalTermFreq;
			}

			public DocsEnum docs(Bits skipDocs, DocsEnum reuse, int flags)
				throws IOException
			{
				currentFrame.decodeMetaData();
				return postingsReader.docs(fieldInfo, currentFrame.termState, skipDocs, reuse, flags);
			}

			public DocsAndPositionsEnum docsAndPositions(Bits skipDocs, DocsAndPositionsEnum reuse, int flags)
				throws IOException
			{
				if (fieldInfo.getIndexOptions().compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) < 0)
				{
					return null;
				} else
				{
					currentFrame.decodeMetaData();
					return postingsReader.docsAndPositions(fieldInfo, currentFrame.termState, skipDocs, reuse, flags);
				}
			}

			private int getState()
			{
				int state = currentFrame.state;
				for (int idx = 0; idx < currentFrame.suffix; idx++)
				{
					state = runAutomaton.step(state, currentFrame.suffixBytes[currentFrame.startBytePos + idx] & 0xff);
					if (!$assertionsDisabled && state == -1)
						throw new AssertionError();
				}

				return state;
			}

			private void seekToStartTerm(BytesRef target)
				throws IOException
			{
				if (!$assertionsDisabled && currentFrame.ord != 0)
					throw new AssertionError();
				if (term.length < target.length)
					term.bytes = ArrayUtil.grow(term.bytes, target.length);
				org.apache.lucene.util.fst.FST.Arc arc = arcs[0];
				if (!$assertionsDisabled && arc != currentFrame.arc)
					throw new AssertionError();
				int idx = 0;
label0:
				do
				{
label1:
					{
						if (idx > target.length)
							break label0;
						int savePos;
						int saveStartBytePos;
						int saveSuffix;
						long saveLastSubFP;
						int saveTermBlockOrd;
						int cmp;
						do
						{
							savePos = currentFrame.suffixesReader.getPosition();
							saveStartBytePos = currentFrame.startBytePos;
							saveSuffix = currentFrame.suffix;
							saveLastSubFP = currentFrame.lastSubFP;
							saveTermBlockOrd = currentFrame.termState.termBlockOrd;
							boolean isSubBlock = currentFrame.next();
							term.length = currentFrame.prefix + currentFrame.suffix;
							if (term.bytes.length < term.length)
								term.bytes = ArrayUtil.grow(term.bytes, term.length);
							System.arraycopy(currentFrame.suffixBytes, currentFrame.startBytePos, term.bytes, currentFrame.prefix, currentFrame.suffix);
							if (isSubBlock && StringHelper.startsWith(target, term))
							{
								currentFrame = pushFrame(getState());
								break label1;
							}
							cmp = term.compareTo(target);
							if (cmp >= 0)
								break;
							if (currentFrame.nextEnt == currentFrame.entCount)
								if (!currentFrame.isLastInFloor)
									currentFrame.loadNextFloorBlock();
								else
									return;
						} while (true);
						if (cmp == 0)
						{
							return;
						} else
						{
							currentFrame.nextEnt--;
							currentFrame.lastSubFP = saveLastSubFP;
							currentFrame.startBytePos = saveStartBytePos;
							currentFrame.suffix = saveSuffix;
							currentFrame.suffixesReader.setPosition(savePos);
							currentFrame.termState.termBlockOrd = saveTermBlockOrd;
							System.arraycopy(currentFrame.suffixBytes, currentFrame.startBytePos, term.bytes, currentFrame.prefix, currentFrame.suffix);
							term.length = currentFrame.prefix + currentFrame.suffix;
							return;
						}
					}
					idx++;
				} while (true);
				if (!$assertionsDisabled)
					throw new AssertionError();
				else
					return;
			}

			public BytesRef next()
				throws IOException
			{
				int state;
				do
label0:
					do
					{
						boolean isSubBlock;
label1:
						{
							if (currentFrame.nextEnt == currentFrame.entCount)
							{
								if (!currentFrame.isLastInFloor)
								{
									currentFrame.loadNextFloorBlock();
								} else
								{
									if (currentFrame.ord == 0)
										return null;
									long lastFP = currentFrame.fpOrig;
									currentFrame = stack[currentFrame.ord - 1];
									if (!$assertionsDisabled && currentFrame.lastSubFP != lastFP)
										throw new AssertionError();
								}
								continue;
							}
							isSubBlock = currentFrame.next();
							if (currentFrame.suffix != 0)
							{
								int label = currentFrame.suffixBytes[currentFrame.startBytePos] & 0xff;
								do
								{
									if (label <= currentFrame.curTransitionMax)
										break;
									if (currentFrame.transitionIndex >= currentFrame.transitions.length - 1)
									{
										currentFrame.isLastInFloor = true;
										currentFrame.nextEnt = currentFrame.entCount;
										continue label0;
									}
									currentFrame.transitionIndex++;
									currentFrame.curTransitionMax = currentFrame.transitions[currentFrame.transitionIndex].getMax();
								} while (true);
							}
							if (compiledAutomaton.commonSuffixRef == null || isSubBlock)
								break label1;
							int termLen = currentFrame.prefix + currentFrame.suffix;
							if (termLen < compiledAutomaton.commonSuffixRef.length)
								continue;
							byte suffixBytes[] = currentFrame.suffixBytes;
							byte commonSuffixBytes[] = compiledAutomaton.commonSuffixRef.bytes;
							int lenInPrefix = compiledAutomaton.commonSuffixRef.length - currentFrame.suffix;
							if (!$assertionsDisabled && compiledAutomaton.commonSuffixRef.offset != 0)
								throw new AssertionError();
							int commonSuffixBytesPos = 0;
							int suffixBytesPos;
							if (lenInPrefix > 0)
							{
								byte termBytes[] = term.bytes;
								int termBytesPos = currentFrame.prefix - lenInPrefix;
								if (!$assertionsDisabled && termBytesPos < 0)
									throw new AssertionError();
								for (int termBytesPosEnd = currentFrame.prefix; termBytesPos < termBytesPosEnd;)
									if (termBytes[termBytesPos++] != commonSuffixBytes[commonSuffixBytesPos++])
										continue label0;

								suffixBytesPos = currentFrame.startBytePos;
							} else
							{
								suffixBytesPos = (currentFrame.startBytePos + currentFrame.suffix) - compiledAutomaton.commonSuffixRef.length;
							}
							int commonSuffixBytesPosEnd = compiledAutomaton.commonSuffixRef.length;
							do
								if (commonSuffixBytesPos >= commonSuffixBytesPosEnd)
									break label1;
							while (suffixBytes[suffixBytesPos++] == commonSuffixBytes[commonSuffixBytesPos++]);
							continue;
						}
						state = currentFrame.state;
						for (int idx = 0; idx < currentFrame.suffix; idx++)
						{
							state = runAutomaton.step(state, currentFrame.suffixBytes[currentFrame.startBytePos + idx] & 0xff);
							if (state == -1)
								continue label0;
						}

						if (!isSubBlock)
							break;
						copyTerm();
						currentFrame = pushFrame(state);
					} while (true);
				while (!runAutomaton.isAccept(state));
				copyTerm();
				if (!$assertionsDisabled && savedStartTerm != null && term.compareTo(savedStartTerm) <= 0)
					throw new AssertionError((new StringBuilder()).append("saveStartTerm=").append(savedStartTerm.utf8ToString()).append(" term=").append(term.utf8ToString()).toString());
				else
					return term;
			}

			private void copyTerm()
			{
				int len = currentFrame.prefix + currentFrame.suffix;
				if (term.bytes.length < len)
					term.bytes = ArrayUtil.grow(term.bytes, len);
				System.arraycopy(currentFrame.suffixBytes, currentFrame.startBytePos, term.bytes, currentFrame.prefix, currentFrame.suffix);
				term.length = len;
			}

			public Comparator getComparator()
			{
				return BytesRef.getUTF8SortedAsUnicodeComparator();
			}

			public boolean seekExact(BytesRef text, boolean useCache)
			{
				throw new UnsupportedOperationException();
			}

			public void seekExact(long ord)
			{
				throw new UnsupportedOperationException();
			}

			public long ord()
			{
				throw new UnsupportedOperationException();
			}

			public org.apache.lucene.index.TermsEnum.SeekStatus seekCeil(BytesRef text, boolean useCache)
			{
				throw new UnsupportedOperationException();
			}





			public IntersectEnum(CompiledAutomaton compiled, BytesRef startTerm)
				throws IOException
			{
				this$1 = FieldReader.this;
				super();
				arcs = new org.apache.lucene.util.fst.FST.Arc[5];
				runAutomaton = compiled.runAutomaton;
				compiledAutomaton = compiled;
				in = 0.in.clone();
				stack = new Frame[5];
				for (int idx = 0; idx < stack.length; idx++)
					stack[idx] = new Frame(idx);

				for (int arcIdx = 0; arcIdx < arcs.length; arcIdx++)
					arcs[arcIdx] = new org.apache.lucene.util.fst.FST.Arc();

				if (index == null)
					fstReader = null;
				else
					fstReader = index.getBytesReader(0);
				org.apache.lucene.util.fst.FST.Arc arc = index.getFirstArc(arcs[0]);
				if (!$assertionsDisabled && !arc.isFinal())
					throw new AssertionError();
				Frame f = stack[0];
				f.fp = f.fpOrig = rootBlockFP;
				f.prefix = 0;
				f.setState(runAutomaton.getInitialState());
				f.arc = arc;
				f.outputPrefix = (BytesRef)arc.output;
				f.load(rootCode);
				if (!$assertionsDisabled && !setSavedStartTerm(startTerm))
					throw new AssertionError();
				currentFrame = f;
				if (startTerm != null)
					seekToStartTerm(startTerm);
			}
		}


		final long numTerms;
		final FieldInfo fieldInfo;
		final long sumTotalTermFreq;
		final long sumDocFreq;
		final int docCount;
		final long indexStartFP;
		final long rootBlockFP;
		final BytesRef rootCode;
		private final FST index;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/BlockTreeTermsReader.desiredAssertionStatus();
		final BlockTreeTermsReader this$0;

		public Stats computeStats()
			throws IOException
		{
			return (new SegmentTermsEnum()).computeBlockStats();
		}

		public Comparator getComparator()
		{
			return BytesRef.getUTF8SortedAsUnicodeComparator();
		}

		public boolean hasOffsets()
		{
			return fieldInfo.getIndexOptions().compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS) >= 0;
		}

		public boolean hasPositions()
		{
			return fieldInfo.getIndexOptions().compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) >= 0;
		}

		public boolean hasPayloads()
		{
			return fieldInfo.hasPayloads();
		}

		public TermsEnum iterator(TermsEnum reuse)
			throws IOException
		{
			return new SegmentTermsEnum();
		}

		public long size()
		{
			return numTerms;
		}

		public long getSumTotalTermFreq()
		{
			return sumTotalTermFreq;
		}

		public long getSumDocFreq()
		{
			return sumDocFreq;
		}

		public int getDocCount()
		{
			return docCount;
		}

		public TermsEnum intersect(CompiledAutomaton compiled, BytesRef startTerm)
			throws IOException
		{
			if (compiled.type != org.apache.lucene.util.automaton.CompiledAutomaton.AUTOMATON_TYPE.NORMAL)
				throw new IllegalArgumentException("please use CompiledAutomaton.getTermsEnum instead");
			else
				return new IntersectEnum(compiled, startTerm);
		}



		FieldReader(FieldInfo fieldInfo, long numTerms, BytesRef rootCode, long sumTotalTermFreq, 
				long sumDocFreq, int docCount, long indexStartFP, IndexInput indexIn)
			throws IOException
		{
			this$0 = BlockTreeTermsReader.this;
			super();
			if (!$assertionsDisabled && numTerms <= 0L)
				throw new AssertionError();
			this.fieldInfo = fieldInfo;
			this.numTerms = numTerms;
			this.sumTotalTermFreq = sumTotalTermFreq;
			this.sumDocFreq = sumDocFreq;
			this.docCount = docCount;
			this.indexStartFP = indexStartFP;
			this.rootCode = rootCode;
			rootBlockFP = (new ByteArrayDataInput(rootCode.bytes, rootCode.offset, rootCode.length)).readVLong() >>> 2;
			if (indexIn != null)
			{
				IndexInput clone = indexIn.clone();
				clone.seek(indexStartFP);
				index = new FST(clone, ByteSequenceOutputs.getSingleton());
			} else
			{
				index = null;
			}
		}
	}

	public static class Stats
	{

		public int indexNodeCount;
		public int indexArcCount;
		public int indexNumBytes;
		public long totalTermCount;
		public long totalTermBytes;
		public int nonFloorBlockCount;
		public int floorBlockCount;
		public int floorSubBlockCount;
		public int mixedBlockCount;
		public int termsOnlyBlockCount;
		public int subBlocksOnlyBlockCount;
		public int totalBlockCount;
		public int blockCountByPrefixLen[];
		private int startBlockCount;
		private int endBlockCount;
		public long totalBlockSuffixBytes;
		public long totalBlockStatsBytes;
		public long totalBlockOtherBytes;
		public final String segment;
		public final String field;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/BlockTreeTermsReader.desiredAssertionStatus();

		void startBlock(FieldReader.SegmentTermsEnum.Frame frame, boolean isFloor)
		{
			totalBlockCount++;
			if (isFloor)
			{
				if (frame.fp == frame.fpOrig)
					floorBlockCount++;
				floorSubBlockCount++;
			} else
			{
				nonFloorBlockCount++;
			}
			if (blockCountByPrefixLen.length <= frame.prefix)
				blockCountByPrefixLen = ArrayUtil.grow(blockCountByPrefixLen, 1 + frame.prefix);
			blockCountByPrefixLen[frame.prefix]++;
			startBlockCount++;
			totalBlockSuffixBytes += frame.suffixesReader.length();
			totalBlockStatsBytes += frame.statsReader.length();
		}

		void endBlock(FieldReader.SegmentTermsEnum.Frame frame)
		{
			int termCount = frame.isLeafBlock ? frame.entCount : frame.state.termBlockOrd;
			int subBlockCount = frame.entCount - termCount;
			totalTermCount += termCount;
			if (termCount != 0 && subBlockCount != 0)
				mixedBlockCount++;
			else
			if (termCount != 0)
				termsOnlyBlockCount++;
			else
			if (subBlockCount != 0)
				subBlocksOnlyBlockCount++;
			else
				throw new IllegalStateException();
			endBlockCount++;
			long otherBytes = frame.fpEnd - frame.fp - (long)frame.suffixesReader.length() - (long)frame.statsReader.length();
			if (!$assertionsDisabled && otherBytes <= 0L)
			{
				throw new AssertionError((new StringBuilder()).append("otherBytes=").append(otherBytes).append(" frame.fp=").append(frame.fp).append(" frame.fpEnd=").append(frame.fpEnd).toString());
			} else
			{
				totalBlockOtherBytes += otherBytes;
				return;
			}
		}

		void term(BytesRef term)
		{
			totalTermBytes += term.length;
		}

		void finish()
		{
			if (!$assertionsDisabled && startBlockCount != endBlockCount)
				throw new AssertionError((new StringBuilder()).append("startBlockCount=").append(startBlockCount).append(" endBlockCount=").append(endBlockCount).toString());
			if (!$assertionsDisabled && totalBlockCount != floorSubBlockCount + nonFloorBlockCount)
				throw new AssertionError((new StringBuilder()).append("floorSubBlockCount=").append(floorSubBlockCount).append(" nonFloorBlockCount=").append(nonFloorBlockCount).append(" totalBlockCount=").append(totalBlockCount).toString());
			if (!$assertionsDisabled && totalBlockCount != mixedBlockCount + termsOnlyBlockCount + subBlocksOnlyBlockCount)
				throw new AssertionError((new StringBuilder()).append("totalBlockCount=").append(totalBlockCount).append(" mixedBlockCount=").append(mixedBlockCount).append(" subBlocksOnlyBlockCount=").append(subBlocksOnlyBlockCount).append(" termsOnlyBlockCount=").append(termsOnlyBlockCount).toString());
			else
				return;
		}

		public String toString()
		{
			ByteArrayOutputStream bos;
			UnsupportedEncodingException bogus;
			bos = new ByteArrayOutputStream(1024);
			PrintStream out;
			try
			{
				out = new PrintStream(bos, false, "UTF-8");
			}
			// Misplaced declaration of an exception variable
			catch (UnsupportedEncodingException bogus)
			{
				throw new RuntimeException(bogus);
			}
			out.println("  index FST:");
			out.println((new StringBuilder()).append("    ").append(indexNodeCount).append(" nodes").toString());
			out.println((new StringBuilder()).append("    ").append(indexArcCount).append(" arcs").toString());
			out.println((new StringBuilder()).append("    ").append(indexNumBytes).append(" bytes").toString());
			out.println("  terms:");
			out.println((new StringBuilder()).append("    ").append(totalTermCount).append(" terms").toString());
			out.println((new StringBuilder()).append("    ").append(totalTermBytes).append(" bytes").append(totalTermCount == 0L ? "" : (new StringBuilder()).append(" (").append(String.format(Locale.ROOT, "%.1f", new Object[] {
				Double.valueOf((double)totalTermBytes / (double)totalTermCount)
			})).append(" bytes/term)").toString()).toString());
			out.println("  blocks:");
			out.println((new StringBuilder()).append("    ").append(totalBlockCount).append(" blocks").toString());
			out.println((new StringBuilder()).append("    ").append(termsOnlyBlockCount).append(" terms-only blocks").toString());
			out.println((new StringBuilder()).append("    ").append(subBlocksOnlyBlockCount).append(" sub-block-only blocks").toString());
			out.println((new StringBuilder()).append("    ").append(mixedBlockCount).append(" mixed blocks").toString());
			out.println((new StringBuilder()).append("    ").append(floorBlockCount).append(" floor blocks").toString());
			out.println((new StringBuilder()).append("    ").append(totalBlockCount - floorSubBlockCount).append(" non-floor blocks").toString());
			out.println((new StringBuilder()).append("    ").append(floorSubBlockCount).append(" floor sub-blocks").toString());
			out.println((new StringBuilder()).append("    ").append(totalBlockSuffixBytes).append(" term suffix bytes").append(totalBlockCount == 0 ? "" : (new StringBuilder()).append(" (").append(String.format(Locale.ROOT, "%.1f", new Object[] {
				Double.valueOf((double)totalBlockSuffixBytes / (double)totalBlockCount)
			})).append(" suffix-bytes/block)").toString()).toString());
			out.println((new StringBuilder()).append("    ").append(totalBlockStatsBytes).append(" term stats bytes").append(totalBlockCount == 0 ? "" : (new StringBuilder()).append(" (").append(String.format(Locale.ROOT, "%.1f", new Object[] {
				Double.valueOf((double)totalBlockStatsBytes / (double)totalBlockCount)
			})).append(" stats-bytes/block)").toString()).toString());
			out.println((new StringBuilder()).append("    ").append(totalBlockOtherBytes).append(" other bytes").append(totalBlockCount == 0 ? "" : (new StringBuilder()).append(" (").append(String.format(Locale.ROOT, "%.1f", new Object[] {
				Double.valueOf((double)totalBlockOtherBytes / (double)totalBlockCount)
			})).append(" other-bytes/block)").toString()).toString());
			if (totalBlockCount != 0)
			{
				out.println("    by prefix length:");
				int total = 0;
				for (int prefix = 0; prefix < blockCountByPrefixLen.length; prefix++)
				{
					int blockCount = blockCountByPrefixLen[prefix];
					total += blockCount;
					if (blockCount != 0)
						out.println((new StringBuilder()).append("      ").append(String.format(Locale.ROOT, "%2d", new Object[] {
							Integer.valueOf(prefix)
						})).append(": ").append(blockCount).toString());
				}

				if (!$assertionsDisabled && totalBlockCount != total)
					throw new AssertionError();
			}
			return bos.toString("UTF-8");
			total;
			throw new RuntimeException(total);
		}


		Stats(String segment, String field)
		{
			blockCountByPrefixLen = new int[10];
			this.segment = segment;
			this.field = field;
		}
	}


	private final IndexInput in;
	private final PostingsReaderBase postingsReader;
	private final TreeMap fields;
	protected long dirOffset;
	protected long indexDirOffset;
	private String segment;
	final Outputs fstOutputs;
	final BytesRef NO_OUTPUT;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/BlockTreeTermsReader.desiredAssertionStatus();

	public BlockTreeTermsReader(Directory dir, FieldInfos fieldInfos, SegmentInfo info, PostingsReaderBase postingsReader, IOContext ioContext, String segmentSuffix, int indexDivisor)
		throws IOException
	{
		boolean success;
		IndexInput indexIn;
		fields = new TreeMap();
		fstOutputs = ByteSequenceOutputs.getSingleton();
		NO_OUTPUT = (BytesRef)fstOutputs.getNoOutput();
		this.postingsReader = postingsReader;
		segment = info.name;
		in = dir.openInput(IndexFileNames.segmentFileName(segment, segmentSuffix, "tim"), ioContext);
		success = false;
		indexIn = null;
		readHeader(in);
		if (indexDivisor != -1)
		{
			indexIn = dir.openInput(IndexFileNames.segmentFileName(segment, segmentSuffix, "tip"), ioContext);
			readIndexHeader(indexIn);
		}
		postingsReader.init(in);
		seekDir(in, dirOffset);
		if (indexDivisor != -1)
			seekDir(indexIn, indexDirOffset);
		int numFields = in.readVInt();
		if (numFields < 0)
			throw new CorruptIndexException((new StringBuilder()).append("invalid numFields: ").append(numFields).append(" (resource=").append(in).append(")").toString());
		for (int i = 0; i < numFields; i++)
		{
			int field = in.readVInt();
			long numTerms = in.readVLong();
			if (!$assertionsDisabled && numTerms < 0L)
				throw new AssertionError();
			int numBytes = in.readVInt();
			BytesRef rootCode = new BytesRef(new byte[numBytes]);
			in.readBytes(rootCode.bytes, 0, numBytes);
			rootCode.length = numBytes;
			FieldInfo fieldInfo = fieldInfos.fieldInfo(field);
			if (!$assertionsDisabled && fieldInfo == null)
				throw new AssertionError((new StringBuilder()).append("field=").append(field).toString());
			long sumTotalTermFreq = fieldInfo.getIndexOptions() != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY ? in.readVLong() : -1L;
			long sumDocFreq = in.readVLong();
			int docCount = in.readVInt();
			if (docCount < 0 || docCount > info.getDocCount())
				throw new CorruptIndexException((new StringBuilder()).append("invalid docCount: ").append(docCount).append(" maxDoc: ").append(info.getDocCount()).append(" (resource=").append(in).append(")").toString());
			if (sumDocFreq < (long)docCount)
				throw new CorruptIndexException((new StringBuilder()).append("invalid sumDocFreq: ").append(sumDocFreq).append(" docCount: ").append(docCount).append(" (resource=").append(in).append(")").toString());
			if (sumTotalTermFreq != -1L && sumTotalTermFreq < sumDocFreq)
				throw new CorruptIndexException((new StringBuilder()).append("invalid sumTotalTermFreq: ").append(sumTotalTermFreq).append(" sumDocFreq: ").append(sumDocFreq).append(" (resource=").append(in).append(")").toString());
			long indexStartFP = indexDivisor == -1 ? 0L : indexIn.readVLong();
			FieldReader previous = (FieldReader)fields.put(fieldInfo.name, new FieldReader(fieldInfo, numTerms, rootCode, sumTotalTermFreq, sumDocFreq, docCount, indexStartFP, indexIn));
			if (previous != null)
				throw new CorruptIndexException((new StringBuilder()).append("duplicate field: ").append(fieldInfo.name).append(" (resource=").append(in).append(")").toString());
		}

		if (indexDivisor != -1)
			indexIn.close();
		success = true;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				indexIn, this
			});
		break MISSING_BLOCK_LABEL_784;
		Exception exception;
		exception;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				indexIn, this
			});
		throw exception;
	}

	protected void readHeader(IndexInput input)
		throws IOException
	{
		CodecUtil.checkHeader(input, "BLOCK_TREE_TERMS_DICT", 0, 0);
		dirOffset = input.readLong();
	}

	protected void readIndexHeader(IndexInput input)
		throws IOException
	{
		CodecUtil.checkHeader(input, "BLOCK_TREE_TERMS_INDEX", 0, 0);
		indexDirOffset = input.readLong();
	}

	protected void seekDir(IndexInput input, long dirOffset)
		throws IOException
	{
		input.seek(dirOffset);
	}

	public void close()
		throws IOException
	{
		IOUtils.close(new Closeable[] {
			in, postingsReader
		});
		fields.clear();
		break MISSING_BLOCK_LABEL_41;
		Exception exception;
		exception;
		fields.clear();
		throw exception;
	}

	public Iterator iterator()
	{
		return Collections.unmodifiableSet(fields.keySet()).iterator();
	}

	public Terms terms(String field)
		throws IOException
	{
		if (!$assertionsDisabled && field == null)
			throw new AssertionError();
		else
			return (Terms)fields.get(field);
	}

	public int size()
	{
		return fields.size();
	}

	String brToString(BytesRef b)
	{
		if (b == null)
			return "null";
		return (new StringBuilder()).append(b.utf8ToString()).append(" ").append(b).toString();
		Throwable t;
		t;
		return b.toString();
	}




}
