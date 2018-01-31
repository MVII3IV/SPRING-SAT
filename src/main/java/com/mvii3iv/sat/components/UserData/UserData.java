package com.mvii3iv.sat.components.UserData;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mvii3iv.sat.components.incomes.Incomes;
import com.mvii3iv.sat.components.login.User;

import java.util.List;

public class UserData {

    private WebClient webClient;
    private HtmlPage browser;
    private List<Incomes> bills;
    private User user;
    private boolean ready;

    public UserData() {
    }

    public UserData(WebClient webClient, HtmlPage browser, List<Incomes> bills, User user, boolean ready) {
        this.webClient = webClient;
        this.browser = browser;
        this.bills = bills;
        this.user = user;
        this.ready = ready;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public HtmlPage getBrowser() {
        return browser;
    }

    public void setBrowser(HtmlPage browser) {
        this.browser = browser;
    }

    public List<Incomes> getBills() {
        return bills;
    }

    public void setBills(List<Incomes> bills) {
        this.bills = bills;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
