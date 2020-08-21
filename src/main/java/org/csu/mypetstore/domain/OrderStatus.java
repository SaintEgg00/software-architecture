package org.csu.mypetstore.domain;

import java.io.Serializable;

public class OrderStatus implements Serializable {
    public String orderid;
    public String linenum;
    public String timestamp;
    public String status;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getLinenum() {
        return linenum;
    }

    public void setLinenum(String linenum) {
        this.linenum = linenum;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "orderid='" + orderid + '\'' +
                ", linenum='" + linenum + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
