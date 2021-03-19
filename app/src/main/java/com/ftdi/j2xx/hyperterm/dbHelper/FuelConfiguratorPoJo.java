package com.ftdi.j2xx.hyperterm.dbHelper;

public class FuelConfiguratorPoJo
{
    String date;
    String eventType;
    String data;

    public FuelConfiguratorPoJo(String date,String eventType, String data)
    {
        this.date = date;
        this.eventType = eventType;
        this.data = data;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
