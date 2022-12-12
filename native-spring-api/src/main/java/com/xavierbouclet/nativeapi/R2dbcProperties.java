package com.xavierbouclet.nativeapi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Objects;

//@ConfigurationProperties(prefix = "spring.r2dbc")
public final  class R2dbcProperties {

    private final String url;
    private final String username;
    private final String password;

    public R2dbcProperties(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String url() {
        return url;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (R2dbcProperties) obj;
        return Objects.equals(this.url, that.url) &&
                Objects.equals(this.username, that.username) &&
                Objects.equals(this.password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, username, password);
    }

    @Override
    public String toString() {
        return "R2dbcProperties[" +
                "url=" + url + ", " +
                "username=" + username + ", " +
                "password=" + password + ']';
    }

}
