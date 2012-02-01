using System;
using System.Collections.Generic;
using System.Web;
using System.Web.Services;
using Microsoft.SqlServer;
using Devart.Data.MySql;

namespace ExtReminderService
{
    /// <summary>
    /// Summary description for Service1
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class Service1 : System.Web.Services.WebService
    {
        MySqlConnection connect;
        //MySqlDataReader dataReader;
        //MySqlCommand maxInserts;
        MySqlCommand insertAlarm;

        [WebMethod]
        /*public string LipaNjiva(string ime, string ura, string minuta)
        {
            //return ime+" "+ura+" "+minuta;
            return "SIOS";
        }*/
        
        public string AddUser(string ime, string ura, string minuta)
        {
            try
            {
                string myConnection = "SERVER=studsrv.uni-mb.si;" + "DATABASE=varnepoti;" + "UID=ronzyfonzy;" + "PASSWORD=snopy02;";
                connect = new MySqlConnection(myConnection);
                connect.Open();

                /*maxInserts = connect.CreateCommand();
                maxInserts.CommandText = "SELECT MAX(id) AS max FROM EXT_REMINDER;";
                dataReader = maxInserts.ExecuteReader();
                dataReader.Read();
                int max = Convert.ToInt32(dataReader["max"].ToString());
                dataReader.Close();*/

                insertAlarm = connect.CreateCommand();
                //INSERT INTO `EXT_REMINDER` (`ime`, `ura`, `minuta`) VALUES ('test1', '23', '12')
                //insertAlarm.CommandText = "INSERT INTO EXT_REMINDER VALUES(" + 1 + ", '" + ime + "', '" + ura + "', '" + minuta + "');";
                insertAlarm.CommandText = "INSERT INTO `EXT_REMINDER` (`ime`, `ura`, `minuta`) VALUES ('" + ime + "', '" + ura + "', '" + minuta + "');";
                insertAlarm.ExecuteNonQuery();
                connect.Close();

                return "narejeno";
            }
            catch (Exception e)
            {
                return "ni_narejeno";
            }
        }
    }
}