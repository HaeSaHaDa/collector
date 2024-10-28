package solomonm.ugo.collector.dbtoexcel.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class PreviousMonthConfig {
    // 전달의 첫날과 마지막 날을 구함
    static LocalDate firstDayOfLastMonth = LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
    static LocalDate lastDayOfLastMonth = LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

    // 형식을 'yyyyMMdd'로 변환
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static String startOfLastMonth = firstDayOfLastMonth.format(formatter);
    public static String endOfLastMonth = lastDayOfLastMonth.format(formatter);

    // 현재 날짜 기준 전달의 연월을 'yyyyMM' 형식으로 구함
    static LocalDate lastMonth = LocalDate.now().minusMonths(1);
    public static String lastMonthString = lastMonth.format(DateTimeFormatter.ofPattern("yyyyMM"));
}
