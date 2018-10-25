// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldsQuery.java

package org.apache.lucene.queryparser.surround.query;

import java.util.*;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			SrndQuery, OrQuery, BasicQueryFactory

public class FieldsQuery extends SrndQuery
{

	private SrndQuery q;
	private List fieldNames;
	private final char fieldOp;
	private final String OrOperatorName = "OR";

	public FieldsQuery(SrndQuery q, List fieldNames, char fieldOp)
	{
		this.q = q;
		this.fieldNames = fieldNames;
		this.fieldOp = fieldOp;
	}

	public FieldsQuery(SrndQuery q, String fieldName, char fieldOp)
	{
		this.q = q;
		fieldNames = new ArrayList();
		fieldNames.add(fieldName);
		this.fieldOp = fieldOp;
	}

	public boolean isFieldsSubQueryAcceptable()
	{
		return false;
	}

	public Query makeLuceneQueryNoBoost(BasicQueryFactory qf)
	{
		if (fieldNames.size() == 1)
			return q.makeLuceneQueryFieldNoBoost((String)fieldNames.get(0), qf);
		List queries = new ArrayList();
		SrndQuery qc;
		for (Iterator fni = getFieldNames().listIterator(); fni.hasNext(); queries.add(new FieldsQuery(qc, (String)fni.next(), fieldOp)))
			qc = q.clone();

		OrQuery oq = new OrQuery(queries, true, "OR");
		return oq.makeLuceneQueryField(null, qf);
	}

	public Query makeLuceneQueryFieldNoBoost(String fieldName, BasicQueryFactory qf)
	{
		return makeLuceneQueryNoBoost(qf);
	}

	public List getFieldNames()
	{
		return fieldNames;
	}

	public char getFieldOperator()
	{
		return fieldOp;
	}

	public String toString()
	{
		StringBuilder r = new StringBuilder();
		r.append("(");
		fieldNamesToString(r);
		r.append(q.toString());
		r.append(")");
		return r.toString();
	}

	protected void fieldNamesToString(StringBuilder r)
	{
		for (Iterator fni = getFieldNames().listIterator(); fni.hasNext(); r.append(getFieldOperator()))
			r.append((String)fni.next());

	}
}
