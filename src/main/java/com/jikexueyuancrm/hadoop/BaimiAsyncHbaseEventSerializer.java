package com.jikexueyuancrm.hadoop;

import java.util.ArrayList;
import java.util.List;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.FlumeException;
import org.hbase.async.AtomicIncrementRequest;
import org.hbase.async.PutRequest;
import org.apache.flume.conf.ComponentConfiguration;
import org.apache.flume.sink.hbase.AsyncHbaseEventSerializer;
import org.apache.flume.sink.hbase.SimpleHbaseEventSerializer.KeyType;
import org.apache.flume.sink.hbase.SimpleRowKeyGenerator;

import com.google.common.base.Charsets;

/*
 * 
 * 
 * 
flume配置文件
a1.sources  = r1
a1.sinks =  k1
a1.channels  = c1

#  Describe/configure the source
a1.sources.r1.type  = spooldir
a1.sources.r1.spoolDir  = /home/scut/Downloads/testFlume

# Describe  the sink
a1.sinks.k1.type  = org.apache.flume.sink.hbase.AsyncHBaseSink
a1.sinks.k1.table = Router #设置hbase的表名
a1.sinks.k1.columnFamily = log #设置hbase中的columnFamily
a1.sinks.k1.serializer.payloadColumn=serviceTime,browerOS,clientTime,screenHeight,screenWidth,url,userAgent,mobileDevice,gwId,mac # 设置hbase的column
a1.sinks.k1.serializer = org.apache.flume.sink.hbase.BaimiAsyncHbaseEventSerializer # 设置serializer的处理类

# Use a  channel which buffers events in memory
a1.channels.c1.type  = memory
a1.channels.c1.capacity  = 1000
a1.channels.c1.transactionCapacity  = 100

# Bind the  source and sink to the channel
a1.sources.r1.channels  = c1
a1.sinks.k1.channel  = c1

Spooling Directory Source：监测配置的目录下新增的文件，并将文件中的数据读取出来。
其中，Spool Source有2个注意地方，第一个是拷贝到spool目录下的文件不可以再打开编辑，
第二个是spool目录下不可包含相应的子目录。这个主要用途作为对日志的准实时监控。
*
*
*
*重点可以查看setEent，configure，getActions函数
*
*
*
*/



public class BaimiAsyncHbaseEventSerializer implements AsyncHbaseEventSerializer {
  private byte[] table;
  private byte[] cf;
  private byte[][] payload;
  private byte[][] payloadColumn;
  private final String payloadColumnSplit = "\\^A";
  private byte[] incrementColumn;
  private String rowSuffix;
  private String rowSuffixCol;
  private byte[] incrementRow;
  private KeyType keyType;

  @Override
  public void initialize(byte[] table, byte[] cf) {
	  
	  
    this.table = table;
    this.cf = cf;
  }

  @Override
  public List<PutRequest> getActions() {
    List<PutRequest> actions = new ArrayList<PutRequest>();
    if(payloadColumn != null){
      byte[] rowKey;
      try {
        switch (keyType) {
          case TS:
            rowKey = SimpleRowKeyGenerator.getTimestampKey(rowSuffix);
            break;
          case TSNANO:
            rowKey = SimpleRowKeyGenerator.getNanoTimestampKey(rowSuffix);
            break;
          case RANDOM:
            rowKey = SimpleRowKeyGenerator.getRandomKey(rowSuffix);
            break;
          default:
            rowKey = SimpleRowKeyGenerator.getUUIDKey(rowSuffix);
            break;
        }

	// for 循环，提交所有列和对于数据的put请求。
	for (int i = 0; i < this.payload.length; i++)
	{
        	PutRequest putRequest =  new PutRequest(table, rowKey, cf,payloadColumn[i], payload[i]);
        	actions.add(putRequest);
	}

      } catch (Exception e){
        throw new FlumeException("Could not get row key!", e);
      }
    }
    return actions;
  }

  public List<AtomicIncrementRequest> getIncrements(){
    List<AtomicIncrementRequest> actions = new
        ArrayList<AtomicIncrementRequest>();
    if(incrementColumn != null) {
      AtomicIncrementRequest inc = new AtomicIncrementRequest(table,
          incrementRow, cf, incrementColumn);
      actions.add(inc);
    }
    return actions;
  }

  @Override
  public void cleanUp() {
    // TODO Auto-generated method stub

  }

  @Override
  public void configure(Context context) {
    String pCol = context.getString("payloadColumn", "pCol");
    String iCol = context.getString("incrementColumn", "iCol");
    rowSuffixCol = context.getString("rowPrefixCol", "mac");
    String suffix = context.getString("suffix", "uuid");
    if(pCol != null && !pCol.isEmpty()) {
      if(suffix.equals("timestamp")){
        keyType = KeyType.TS;
      } else if (suffix.equals("random")) {
        keyType = KeyType.RANDOM;
      } else if(suffix.equals("nano")){
        keyType = KeyType.TSNANO;
      } else {
        keyType = KeyType.UUID;
      }
 
     	// 从配置文件中读出column。 
     	String[] pCols = pCol.replace(" ", "").split(",");
     	payloadColumn = new byte[pCols.length][];
     	for (int i = 0; i < pCols.length; i++)
	{
		// 列名转为小写
		payloadColumn[i] = pCols[i].toLowerCase().getBytes(Charsets.UTF_8);
	}
    }

    if(iCol != null && !iCol.isEmpty()) {
      incrementColumn = iCol.getBytes(Charsets.UTF_8);
    }
    incrementRow =
        context.getString("incrementRow", "incRow").getBytes(Charsets.UTF_8);
  }

  @Override
  public void setEvent(Event event) {
	String strBody = new String(event.getBody());
	String[] subBody = strBody.split(this.payloadColumnSplit);
	if (subBody.length == this.payloadColumn.length)
	{
		this.payload = new byte[subBody.length][];
		for (int i = 0; i < subBody.length; i++)
		{
			this.payload[i] = subBody[i].getBytes(Charsets.UTF_8);
			if ((new String(this.payloadColumn[i]).equals(this.rowSuffixCol)))
			{
				// rowkey 前缀是某一列的值,默认情况是mac地址
				this.rowSuffix = subBody[i];
			}
		}
	}
  }

  @Override
  public void configure(ComponentConfiguration conf) {
    // TODO Auto-generated method stub
  }
}