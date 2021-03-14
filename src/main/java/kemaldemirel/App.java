package kemaldemirel;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class App
{

  public static int indexSum(ArrayList<Integer> arr, int i1,Integer x, int i2 )  throws Exception
  {
    if (arr.isEmpty() || arr == null )
    {
        throw new NullPointerException("NullPointerException.");
    }
    else if(i1 == 0 && i2 == 0 && x.equals(0))
    {
        throw new Exception();
    }
    else if( arr.size() <= i1 || arr.size() <= i2  || i1 < 0 || i2 < 0 )
    {
        throw new IndexOutOfBoundsException("IndexOutOfBoundsException.");
    }
    else if( arr.size() <= x.intValue() || x.intValue() < 0)
    {
        throw new IndexOutOfBoundsException("IndexOutOfBoundsException.");
    }
    return arr.get(i1) + arr.get(i2) + arr.get(x.intValue());
  }

  public static void main(String[] args) {
      port(getHerokuAssignedPort());

      get("/", (req, res) -> "Hello to sum");

      post("/compute", (req, res) -> {
        //System.out.println(req.queryParams("input1"));
        //System.out.println(req.queryParams("input2"));

        String input1 = req.queryParams("input1");
        java.util.Scanner sc1 = new java.util.Scanner(input1);
        sc1.useDelimiter("[;\r\n]+");
        ArrayList<Integer> inputList = new ArrayList<>();
        while (sc1.hasNext())
        {
          int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
          inputList.add(value);
        }
        sc1.close();
        System.out.println(inputList);


        String input2 = req.queryParams("input2").replaceAll("\\s","");
        int input2AsInt = Integer.parseInt(input2);

        String input3 = req.queryParams("input3").replaceAll("\\s","");
        int input3AsInt = Integer.parseInt(input3);
        Integer input3AsInteger = input3AsInt;

        String input4 = req.queryParams("input4").replaceAll("\\s","");
        int input4AsInt = Integer.parseInt(input4);

        int result = App.indexSum(inputList, input2AsInt, input3AsInteger, input4AsInt);

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("result", result);
        return new ModelAndView(map, "compute.mustache");
      }, new MustacheTemplateEngine());


      get("/compute",
          (rq, rs) -> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("result", "not computed yet!");
            return new ModelAndView(map, "compute.mustache");
          },
          new MustacheTemplateEngine());
  }

  static int getHerokuAssignedPort() {
      ProcessBuilder processBuilder = new ProcessBuilder();
      if (processBuilder.environment().get("PORT") != null) {
          return Integer.parseInt(processBuilder.environment().get("PORT"));
      }
      return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
  }
}
