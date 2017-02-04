import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {
    public static void main(String[] args) throws Exception {
        Solution s = new Solution();

        s.flyby(36.098592, -112.097796);
    }


    void flyby(double latitude, double longitude) throws Exception {

        if (latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180) {

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -100);
            String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            String API_KEY = "9Jz6tLIeJ0yY9vjbEUWaH9fsXA930J9hspPchute";
            String URL = "https://api.nasa.gov/planetary/earth/assets?lon=" + longitude + "&lat=" + latitude + "&begin=" + modifiedDate + "&api_key=" + API_KEY;
            int daysInBetween = 0;
            String outputJson = getJSONResponse(URL);


            JSONObject jObject = (JSONObject) new JSONParser().parse(outputJson);
            int days = 0;
            JSONArray results = (JSONArray) jObject.get("results");
          
          
            for (int i = 0; i < results.size(); i++) {
                JSONObject p = (JSONObject) results.get(i);
                String fromDate = (String) p.get("date");
                String toDate = (String) p.get("date");

                days += daysBetween(getCalenderObject(toDate.substring(0, 10)), getCalenderObject(fromDate.substring(0, 10)));
              
              
                if (daysInBetween < days)
                    daysInBetween = days;
            }
              days = days/results.size();
          
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, days);

            // sufficient data available
            System.out.println("Next time: " + c.getTime().toString());

        } else {
            System.out.print("Invalid entry");
        }


    }

    private int daysBetween(Calendar day1, Calendar day2) {
        Calendar dayOne = (Calendar) day1.clone(),
                dayTwo = (Calendar) day2.clone();

        if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
            return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR));
        } else {
            if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
                //swap them
                Calendar temp = dayOne;
                dayOne = dayTwo;
                dayTwo = temp;
            }
            int extraDays = 0;

            int dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR);

            while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
                dayOne.add(Calendar.YEAR, -1);
                // getActualMaximum() important for leap years
                extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
            }

            return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOneOriginalYearDays;
        }
    }


    private Calendar getCalenderObject(String input) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(input));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }


    private String getJSONResponse(String url) throws IOException {
        HttpURLConnection urlConnection = null;
        try {
            URL connectionURL = new URL(url);
            urlConnection = (HttpURLConnection) connectionURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-length", "0");
            urlConnection.setUseCaches(false);
            urlConnection.setAllowUserInteraction(false);
            urlConnection.connect();
            int status = urlConnection.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (urlConnection != null) {
                try {
                    urlConnection.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }


}

/* 
Your previous Plain Text content is preserved below:

This is just a simple shared plaintext pad, with no execution capabilities.

When you know what language you'd like to use for your interview,
simply choose it from the dropdown in the top bar.

You can also change the default language your pads are created with
in your account settings: https://coderpad.io/settings

Enjoy your interview!

===== Preface =====

This question is very difficult in C and C++, where there is
insufficient library support to answer it in an hour. If you
prefer to program in one of those languages, please ask us to
provide you with a question designed for those languages instead!


===== Intro =====

Here at Delphix, we admire NASA’s engineering mission. But beyond
that, we can use data from NASA to make predictions about the
future. Solving global warming is unfortunately outside the scope
of an interview question, so your goal is somewhat simpler: use
NASA’s public HTTP APIs to create a function which predicts the
next time a satellite image will be taken of a certain location.
This can be handy if you're trying to get your picture onto online
mapping applications like Google Maps. :-)

You should implement this in whatever language you're most
comfortable with -- just make sure your code is production quality,
well designed, and easy to read.

Finally, please help us by keeping this question and your
answer secret so that every candidate has a fair chance in
future Delphix interviews. Thank you!


===== Steps =====

1.  Choose the language you want to code in from the menu
    labeled "Plain Text" in the top right corner of the
    screen. You will see a "Run" button appear on the top
    left -- clicking this will send your code to a Linux
    server and compile / run it. Output will appear on the
    right side of the screen.
    
    For information about what libraries are available for
    your chosen language, see:

      https://coderpad.io/languages  

2.  Pull up the documentation for the API you'll be using:

      https://api.nasa.gov/ api.html

3.  You'll need an API key in order to query the data from
    NASA. You can use the one that we created:

      9Jz6tLIeJ0yY9vjbEUWaH9fsXA930J9hspPchute

4.  Implement a function flyby() whose method signature
    looks like this (can differ slightly depending on the
    language you chose):

      void flyby(double latitude, double longitude)

    When there is enough data to do so, the function should
    print a prediction for when the next picture will be taken. 
    The prediction should have a date and time based on the
    average time between successive pictures. In pseudocode, the
    prediction method might look like:

      print "Next time: " + (last_date + avg_time_delta)

    You can use the https://api.nasa.gov/api.html#assets API
    to get the information you will need to compute this.
    Note that the NASA documentation mentions that
    avg_time_delta is usually close to 16 days, but we'd
    like you to calculate it since it's not always the same.

    If you want to change the function signature to deal
    with error conditions or some other complexity not
    captured by the one above, go for it! Just add a comment
    telling us what you changed and why.

5.  Add any tests for your code to the main() method of
    your program so that we can easily run them.


====== FAQs =====

Q:  How do I know if my solution is correct?
A:  Make sure you've read the prompt carefully and you're
    convinced your program does what you think it should
    in the common case. If your program does what the prompt 
    dictates, you will get full credit. We do not use an
    auto-grader, so we do not have any values for you to
    check correctness against.
    
Q:  What is Delphix looking for in a solution?
A:  After submitting your code, we'll have a pair of engineers
    evaluate it and determine next steps in the interview process.
    We are looking for correct, easy-to-read, robust code.
    Specifically, ensure your code is idiomatic and laid out
    logically. Ensure it is correct. Ensure it handles all edge
    cases and error cases elegantly.
    
Q:  How should my output be formatted?
A:  Your output should include a date and time in whatever
    format you find easiest. There are no other strict formatting
    constraints (we just inspect the output for correctness).

Q:  Any suggestions of fun locations I can test with?
A:  Sure! Here are a few:

    Fun location           Latitude    Longitude
    ---------------------  ----------  ------------
    Grand Canyon           36.098592   -112.097796
    Niagra Falls           43.078154   -79.075891
    Four Corners Monument  36.998979   -109.045183
    Delphix San Francisco  37.7937007  -122.4039064

Q:  If I need a clarification, who should I ask?
A:  Send all questions to the email address that sent you
    this document, and an engineer at Delphix will get
    back to you ASAP (we're pretty quick during normal
    business hours).

Q:  How long should this question take me?
A:  Approximately 1 hour, but it could take more or less
    depending on your experience with web APIs and the
    language you choose.

Q:  When is this due?
A:  We will begin grading your answer 24 hours after it is
    sent to you, so that is the deadline.

Q:  How do I turn in my solution?
A:  Anything you've typed into this document will be saved.
    Email us when you are done with your solution. We will
    respond confirming we've received the solution within
    24 hours.

Q:  Can I use any external resources to help me?
A:  Absolutely! Feel free to use any online resources you
    like, but please don't collaborate with anyone else.

Q:  Can I use my favorite library in my program?
A:  Unfortunately, there is no way to load external
    libraries into CoderPad, so you must stick to what
    they provide out of the box for your language (although
    they do support for many popular general-use libraries):

      https://coderpad.io/languages

    If you really want to use something that's not
    available, email the person who sent you this link
    and we will work with you to find a solution.

Q:  Why does my program terminate unexpectedly in
    CoderPad, and why can't I read from stdin or pass
    arguments on the command line?
A:  CoderPad places a limit on the runtime and amount of
    output your code can use, but you should be able to
    make your code fit within those limits. You can hard
    code any arguments or inputs to the program in your
    main() method or in your tests.

Q:  I'm a Vim/Emacs fan -- is there any way to use those
    keybindings? What about changing the tab width? Font
    size?
A:  Yes! Hit the button at the bottom of the screen that
    looks like a keyboard.
 */
