package InWork.Operations;

import InWork.DataBase.DataStructure.LiveLoadKTW;

import java.util.ArrayList;

public class Calculations {

    static public ArrayList<LiveLoadKTW> ProductionPlan(ArrayList<ArrayList<LiveLoadKTW>> all){
      int source =0;
      ArrayList<LiveLoadKTW> Plan = new ArrayList<>();
      for(int i =1;i<all.size();i++){
          source=source+1;
          ArrayList<LiveLoadKTW> cluster = all.get(i);
          for (int a=0;a<cluster.size();a++){

          }

      }


        return Plan;
    }
}
