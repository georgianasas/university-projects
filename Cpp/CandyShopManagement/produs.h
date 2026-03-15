#ifndef PRODUS_H
#define PRODUS_H

#include<string>
using namespace std;

class Produs {
private:
    int cod;
    string nume;
    double pret;
public:
    Produs();
    Produs(int cod,string nume,double pret);
    Produs& operator=(const Produs& other);
    int get_cod()const;
    string get_nume()const;
    double get_pret()const;

    void set_cod(int c);
    void set_nume(const string&s);
    void set_pret(double p);

};


#endif
