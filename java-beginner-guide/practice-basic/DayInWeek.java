/** 
 * Enum DayInWeek used to get next day
 * @author Danh Nguyen
 * @version 1.0
 */
enum DayInWeek {
  MONDAY,
  TUESDAY,
  WEDNESDAY,
  THURSDAY,
  FRIDAY,
  SATURDAY,
  SUNDAY;

  /** 
   * Get next day
   * @param day Current day
   * @return Next day
   */
  static DayInWeek getNextDay(DayInWeek day) {
    DayInWeek nextDay = DayInWeek.MONDAY;
    int ordinalNextDay = day.ordinal() + 1;

    for (DayInWeek d: DayInWeek.values()) {
      if (d.ordinal() == ordinalNextDay) {
        return d;
      }
    }

    return nextDay;
  }
}