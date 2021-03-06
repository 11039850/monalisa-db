/*******************************************************************************************
 *	Copyright (c) 2016, zzg.zhou(11039850@qq.com)
 * 
 *  Monalisa is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU Lesser General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.

 *	This program is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU Lesser General Public License for more details.

 *	You should have received a copy of the GNU Lesser General Public License
 *	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************************/
package test.com.tsc9526.monalisa.orm.dialect.postgres.cases;

import java.util.Properties;

import org.testng.annotations.Test;

import com.tsc9526.monalisa.orm.datasource.DBConfig;
import com.tsc9526.monalisa.orm.datasource.DbProp;

import test.com.tsc9526.monalisa.TestConstants;
import test.com.tsc9526.monalisa.orm.dialect.basic.BaseRecordTest;
import test.com.tsc9526.monalisa.orm.dialect.postgres.PostgresDB;

/**
 * 
 * @author zzg.zhou(11039850@qq.com)
 */
@Test(enabled=TestConstants.ENABLE_TEST_WITH_POSTGRES)
public class PostgresRecordTest extends BaseRecordTest implements PostgresDB{ 
	private static final long serialVersionUID = -1974865252589672370L;
  
	@Override
	public DBConfig getDB() {
		return PostgresDB.DB;
	}

	@Override
	public String getInitSqls() {
		return ""+/**~{*/""
				+ "CREATE TABLE \"test_record\" ("
				+ "\r\n  \"record_id\"    serial,"
				+ "\r\n  \"name\"         varchar(128) NOT NULL default 'N0001',"
				+ "\r\n  \"title\"        varchar(128) NULL,"
				+ "\r\n  \"ts_a\"         TIMESTAMP    NULL,"
				+ "\r\n  \"version\"      int4         NOT NULL default 0,"
				+ "\r\n  \"create_time\"  TIMESTAMP    NOT NULL,"
				+ "\r\n  \"create_by\"    varchar(64)  NULL,"
				+ "\r\n  \"update_time\"  TIMESTAMP    NULL,"
				+ "\r\n  \"update_by\"    varchar(64)  NULL,"
				+ "\r\n  "
				+ "\r\n   CONSTRAINT   \"test_record_pk\" PRIMARY KEY (\"record_id\")"
				+ "\r\n) WITH (OIDS=FALSE) ;"
				+ "\r\n"
				+ "\r\nCOMMENT ON COLUMN \"test_record\".\"record_id\" IS '自增主键';"
				+ "\r\nCOMMENT ON COLUMN \"test_record\".\"name\"      IS '名称';"
			 + "\r\n"
				+ "\r\nINSERT INTO test_record(\"name\",\"title\",ts_a,create_time)VALUES('hello','record',now(),now());"
		+ "\r\n"/**}*/;
	}

	@Override
	public String getCleanSqls() {
		return ""+/**~{*/""
			+ "DROP TABLE IF EXISTS test_record"
		+ "\r\n"/**}*/;
	}
	 
	
	protected Properties getConfigProperties() {
		Properties p=new Properties();
		p.put(DbProp.PROP_DB_URL.getFullKey(),                TestConstants.postgresUrl);
		p.put(DbProp.PROP_DB_DATASOURCE_CLASS.getFullKey(),   TestConstants.datasourceClass);
		p.put(DbProp.PROP_DB_USERNAME.getFullKey(),           TestConstants.username);
		p.put(DbProp.PROP_DB_PASSWORD.getFullKey(),           TestConstants.password);
		
		p.put("sql.debug", TestConstants.DEBUG_SQL);
		return p;
	}
}
