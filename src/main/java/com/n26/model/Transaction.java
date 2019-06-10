package com.n26.model;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Transaction {

  private String amount;

  private String timestamp;

  public BigDecimal getAmount() {
    return new BigDecimal(this.amount);
  }

  public long getTimestamp() {

    ISO8601DateFormat dateFormat = new ISO8601DateFormat();

    //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    try {
      Date result1 = dateFormat.parse(this.timestamp);
      return result1.getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return 0l;
  }

  public Boolean validate() {
    Boolean validate = Boolean.TRUE;
    try {
      log.info("{}", this.getAmount().doubleValue());
      ISO8601DateFormat dateFormat = new ISO8601DateFormat();
      dateFormat.parseObject(this.timestamp);
    } catch (Exception e) {
      validate = Boolean.FALSE;
      return validate;
    }
    if (this.getTimestamp() >= Instant.now().toEpochMilli()) {
      validate = Boolean.FALSE;
    }
    return validate;
  }

}
