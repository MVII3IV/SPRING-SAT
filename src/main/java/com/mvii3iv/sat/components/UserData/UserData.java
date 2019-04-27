package com.mvii3iv.sat.components.UserData;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mvii3iv.sat.components.bills.Bills;
import com.mvii3iv.sat.components.user.Users;


import java.util.List;

public class UserData {

    private WebClient webClient;
    private HtmlPage browser;
    private List<Bills> incomes;
    private Users users;
    private boolean ready;

    public UserData() {
    }

    public UserData(WebClient webClient, HtmlPage browser, List<Bills> incomes, Users users, boolean ready) {
        this.webClient = webClient;
        this.browser = browser;
        this.incomes = incomes;
        this.users = users;
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

    public List<Bills> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Bills> incomes) {
        this.incomes = incomes;
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users users) {
        this.users = users;
    }
}
