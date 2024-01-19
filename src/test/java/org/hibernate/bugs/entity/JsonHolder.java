package org.hibernate.bugs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity(name = "JsonHolder")
public class JsonHolder
{
    @Id
    private Long id;

    @Column
    private Long updated = 0L;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> properties;

    @Version
    public long version = 0L;

    public JsonHolder()
    {
    }

    public JsonHolder(Long id, Map<String, Object> properties)
    {
        this.id = id;
        this.properties = properties;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setProperties(Map<String, Object> properties)
    {
        this.properties = properties;
    }

    public long getVersion()
    {
        return version;
    }

    public void updated()
    {
        this.updated++;
    }
}
