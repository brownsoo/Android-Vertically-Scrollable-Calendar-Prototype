# Android Vertically Scrollable Calendar Prototype

This is the Android sample project making the Vertically scrollable calendar for study.
You can see sample [video](https://youtu.be/sHpk8f0WY7U).


## 세로 스크롤 달력 프로토타입 안드로이드 예제

이 저장소는 안드로이드에서 세로 스크롤이 가능한 달력을 만들기 위해 만든 목업샘플입니다.
관련 내용을 제 블로그에서 더 확인하실 수 있습니다. [한수댁 작업실](http://hansune.tistory.com/595)


### Check logic point 1
I create the base date to sync page position with particular date. And I also create the base position in middle of thousands pages.

```java
/** Default year to calculate the page position */
final static int BASE_YEAR = 2015;
/** Default month to calculate the page position */
final static int BASE_MONTH = Calendar.JANUARY;
/** Calendar instance based on default year and month */
final Calendar BASE_CAL;
/** Page numbers to reuse */
final static int PAGES = 5;
/** Loops, I think 1000 may be infinite scroll. */
final static int LOOPS = 1000;
/** position basis */
final static int BASE_POSITION = PAGES * LOOPS / 2;
...
Calendar base = Calendar.getInstance();
base.set(BASE_YEAR, BASE_MONTH, 1);
BASE_CAL = base;
...
```

### Check logic point 2
Then, we can get the particular date by page position.

```java
public YearMonth getYearMonth(int position) {
  Calendar cal = (Calendar)BASE_CAL.clone();
  cal.add(Calendar.MONTH, position - BASE_POSITION);
  return new YearMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
}
```

### Check logic point 3
We can get the particular page position by given date.

```java
/**
* Get the page position by given date
* @param year 4 digits number of year
* @param month month number
* @return page position
*/
public int getPosition(int year, int month) {
  Calendar cal = Calendar.getInstance();
  cal.set(year, month, 1);
  return BASE_POSITION + howFarFromBase(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
}

/**
* How many months exist from the base month to the given values?
* @param year the year to compare with the base year
* @param month the month to compare with the base month
* @return counts of month
*/
private int howFarFromBase(int year, int month) {
  int disY = (year - BASE_YEAR) * 12;
  int disM = month - BASE_MONTH;
  return disY + disM;
}
```


In this project, I use [`VerticalViewPager`](https://github.com/castorflex/VerticalViewPager) for just vertical view pager. And I refer to ['SimpleInfiniteCarousel'](https://github.com/mrleolink/SimpleInfiniteCarousel) to make a simple infinite carousel with ViewPager on Android.

#### Sorry
I am not good at english. please let me know the misspelled comments in my code.^^;




