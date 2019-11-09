package com.imalittletester.objects;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import java.util.List;
import java.util.Objects;

public class Amount {
    private String statusIconColor;
    private String status;
    private String date;
    private String time;
    private String descriptionLogo;
    private String descriptionCompanyName;
    private String category;
    private String categoryBackgroundColor;
    private double amount;
    private Color amountColor;

    public Amount(String statusIconColor, String status, String date, String time, String descriptionLogo, String descriptionCompanyName, String category, String categoryBackgroundColor, double amount, Color amountColor) {
        this.statusIconColor = statusIconColor;
        this.status = status;
        this.date = date;
        this.time = time;
        this.descriptionLogo = descriptionLogo;
        this.descriptionCompanyName = descriptionCompanyName;
        this.category = category;
        this.categoryBackgroundColor = categoryBackgroundColor;
        this.amount = amount;
        this.amountColor = amountColor;
    }

    public Amount(List<WebElement> element) {
        this.statusIconColor = StringUtils.substringAfterLast(element.get(0).findElement(By.cssSelector(".status-pill")).getAttribute(
                "class"), " ");
        this.status = element.get(0).getText();
        this.date = element.get(1).findElement(By.cssSelector("span")).getText();
        this.time = element.get(1).findElement(By.cssSelector(".lighter")).getText();
        this.descriptionLogo = element.get(2).findElement(By.cssSelector("img")).getAttribute("src");
        this.descriptionCompanyName = element.get(2).findElement(By.cssSelector("span")).getText();
        this.category = element.get(3).findElement(By.cssSelector("a")).getText();
        this.categoryBackgroundColor =
                getBackgroundColorForCategory(element.get(3).findElement(By.cssSelector("a")).getAttribute("class"));
        this.amount = getAmountInDouble(element.get(4));
        this.amountColor = Color.fromString(element.get(4).findElement(By.cssSelector("span")).getCssValue("color"));
    }

    private double getAmountInDouble(WebElement element) {
        return Double.parseDouble(StringUtils.substringBeforeLast(element.getText(),
                " ").replaceAll(" ", "").replaceAll("\\+", "").replaceAll(",", ""));
    }

    private String getBackgroundColorForCategory(String categoryClass) {
        switch (StringUtils.substringAfterLast(categoryClass, " ")) {
            case "badge-warning":
                return "yellow";
            case "badge-danger":
                return "red";
            case "badge-primary":
                return "blue";
            case "badge-success":
                return "green";
            default:
                return "";
        }
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount1 = (Amount) o;
        return Double.compare(amount1.amount, amount) == 0 &&
                Objects.equals(statusIconColor, amount1.statusIconColor) &&
                Objects.equals(status, amount1.status) &&
                Objects.equals(date, amount1.date) &&
                Objects.equals(time, amount1.time) &&
                Objects.equals(descriptionLogo, amount1.descriptionLogo) &&
                Objects.equals(descriptionCompanyName, amount1.descriptionCompanyName) &&
                Objects.equals(category, amount1.category) &&
                Objects.equals(categoryBackgroundColor, amount1.categoryBackgroundColor) &&
                Objects.equals(amountColor, amount1.amountColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusIconColor, status, date, time, descriptionLogo, descriptionCompanyName, category, categoryBackgroundColor, amount, amountColor);
    }

    @Override
    public String toString() {
        return "Amount{" +
                "statusIconColor='" + statusIconColor + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", descriptionLogo='" + descriptionLogo + '\'' +
                ", descriptionCompanyName='" + descriptionCompanyName + '\'' +
                ", category='" + category + '\'' +
                ", categoryBackgroundColor='" + categoryBackgroundColor + '\'' +
                ", amount=" + amount +
                ", amountColor=" + amountColor +
                '}';
    }
}

