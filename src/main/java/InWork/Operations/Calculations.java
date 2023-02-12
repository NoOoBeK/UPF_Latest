package InWork.Operations;

public class Calculations {

    // operacje na danych z import PP
    // do danych dociagnac z bazy danych odpowieniej w zaleznosci od fabryki
    // finalne dane ktore beda potrzebne do operacji (sku,liczba palet,max liczby palet,czas produkcji, data startu, godzina startu,rynek docelowy)
    //sku = z import PP
    //liczba palet = QNT(import PP) / net(z database odpowiedniego)
    //max liczby palet = 22500/ gross(z database odpowiedniego)
    //czas produkcji = (SDate+Stime) - (Edate+Etime) / liczba palet
    //Data startu = z import pp
    //Godzina startu z import pp
    //rynek docelowy = dest(database)
    //line z  pp


    //jeżeli liczba pal > max to powielami linie do skutku

    //czas produkcji jest to czas 1 palet powielona linie nowy czas sartu

    //STime max < 1

}
