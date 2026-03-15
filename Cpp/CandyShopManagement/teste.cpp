#include"service.h"
#include "teste.h"
#include"repo.h"
#include<cassert>
#include"RepoFile.h"
#include<fstream>
#include<cstdio>
#include<iostream>
#include"exceptii.h"
#include"validator.h"


using namespace std;


void test_add_getall_repo() {

    Repo repo;

    assert(repo.size() == 0);

    Produs produs1(1,"baton",7);
    Produs produs2(2,"suc",10);

    repo.addItem(produs1);
    repo.addItem(produs2);

    assert(repo.size() == 2);

    vector<Produs> produse=repo.getAll();
    assert(produse[0].get_cod()==1);
    assert(produse[0].get_nume()=="baton");
    assert(produse[1].get_cod()==2);
    assert(produse[1].get_nume()=="suc");

    repo.deleteItem(1);
    assert(repo.size() == 1);

    Produs p(4,"apa",7);
    repo.updateItem(2,p);
    assert(repo.getAll()[0].get_nume()=="apa");
    std::cout<<"Testele pentru repo au trecut cu succes!"<<endl;
}
void testService() {
    std::ofstream out("test_nescris.txt", std::ios::trunc);
    out.close();

    RepoFile repo("test_nescris.txt");
    Service service(repo);

    service.adauga_produs(1,"baton",15);
    service.adauga_produs(2,"suc",7);

    vector<Produs>produse;
    produse=service.get_all();
    assert(produse[0].get_cod()==1);
    assert(produse[0].get_nume()=="baton");
    assert(produse[1].get_cod()==2);
    assert(produse[1].get_nume()=="suc");
    assert(produse.size() == 2);
    cout<<"Testele pentru service au trecut cu succes!"<<endl;
}
void testRepoFile() {
    const string filename="test_file.txt";
    ofstream clearFile(filename);
    clearFile.close();

    RepoFile repo(filename);
    assert(repo.size()==0);

    Produs p1(1,"apa",5);
    Produs p2(2,"suc",6);
    repo.addItem(p1);
    repo.addItem(p2);

    assert(repo.size()==2);
    assert(repo.getAll()[0].get_nume()=="apa");

    Produs pNou(2,"cola",7);
    repo.updateItem(2,pNou);
    assert(repo.getAll()[1].get_nume()=="cola");

    repo.deleteItem(1);
    assert(repo.size()==1);

    RepoFile repo2(filename);
    vector<Produs>produse=repo2.getAll();
    assert(produse.size()==1);
    assert(produse[0].get_cod()==2);
    assert(produse[0].get_nume()=="cola");

    remove(filename.c_str());
    cout<<"Testele pentru fisierul care mosteneste Repo au trecut cu succes!"<<endl;

}
void testUpdateInvalid() {
    Repo repo;
    Produs p(1,"apa",4.5);
    repo.addItem(p);
    try {
        Produs nou(2,"ceai",5);
        repo.updateItem(99,nou);
        assert(false);
    }catch (const ExceptieRepo&e) {
        assert(string(e.what()).find("nu exista")!=string::npos);
        cout<<"Testele actualizare produs inexistent au trecut cu succes!"<<endl;

    }
}
void testAdaugareProdusDuplicat() {
    Repo repo;
    Produs p(1,"suc",3.5);
    repo.addItem(p);
    try {
        repo.addItem(p);
        assert(false);
    }catch (const ExceptieRepo&e) {
        cout<<"Testele pentru produs duplicat au trecut cu succes!"<<endl;
    }
}
void testAchizitieProdus() {
    std::ofstream out("test_nescris.txt", std::ios::trunc);
    out.close();

    RepoFile repo("test_nescris.txt");
    Service service(repo);

    service.adauga_produs(1, "biscuiti", 7.50);
    service.adauga_produs(2, "cola", 5.00);

    try {
        service.achizitieProdus(99, 10.00);
        assert(false);
    }catch (const ExceptieRepo &e) {
        assert(string(e.what()).find("nu exista")!=string::npos);
    }
    try {
        service.achizitieProdus(1, 5.00);
        assert(false);
    }catch (const ExceptieTonomat &e) {
        assert(string(e.what()).find("Fonduri insuficiente!")!=string::npos);
    }
    try {
        std::string rezultat=service.achizitieProdus(2, 5.00);
        assert(rezultat == "Achizitie reusita. Rest: 0.00 lei (monede: )");
    }catch (...) {
        assert(false);
    }
    try {
        std::string rezultat=service.achizitieProdus(1, 10.00);
        assert(rezultat == "Achizitie reusita. Rest: 2.50 lei (monede: 1.00 1.00 0.50 )");
    }catch (...) {
        assert(false);
    }
    std::cout << "Testele pentru achizitionarea unui produs au trecut cu succes!" << std::endl;
}
void testAdaugaMonedeValide() {
    TonomatBani t;
    t.adaugaMonede({1,0.5,5});

    std::string rezultat=t.afiseazaMonedeDisponibile();
    assert(rezultat.find("1.00")!=string::npos);
    assert(rezultat.find("0.50")!=string::npos);
    assert(rezultat.find("5.00")!=string::npos);

    cout<<"Testele pentru adaugare monede valide au trecut cu succes!\n";

}

void testAdaugaMonedeInvalide() {
    TonomatBani t;
    try {
        t.adaugaMonede({2,0.5});
        assert(false);
    }catch (const ExceptieTonomat &e) {
        std::string msg=e.what();
        assert(msg.find("2")!=std::string::npos);
        cout<<"Testele pentru adaugare monede inavlide au trecut cu succes!\n";

    }
}
void testSumaTotalaInTonomat() {
    TonomatBani t;

    double sumaInitiala=t.getSumaTotala();
    assert(std::abs(sumaInitiala-73.50)<0.0001);

    t.adaugaMonede({5,1,0.5});
    double sumaNoua=t.getSumaTotala();
    assert(std::abs(sumaNoua-80.00)<0.0001);

    cout<<"Testele pentru suma totala existenta in tonomat au trecut cu succes!\n";
}
void testValidator() {

    try {
        Validator::valideazaToate(-1, "", -5);
        assert(false); // Dacă nu se aruncă, testul a eșuat
    } catch (const ExceptieProdus& e) {
        std::string msg = e.what();
        std::cout << "[EXCEPTIE CAPTURATA]: " << msg << "\n";

        assert(msg.find("Cod invalid") != std::string::npos);
        assert(msg.find("Nume invalid") != std::string::npos);
        assert(msg.find("Pret invalid") != std::string::npos);

        std::cout << "Testele pentru validator au trecut cu succes!\n";
    } catch (...) {
        assert(false);
    }


    try {
        Validator::valideazaToate(1, "apa", 5.5);
    } catch (...) {
        assert(false);
    }
}

void ruleazaTeste() {
    test_add_getall_repo();
    testService();
    testRepoFile();
    testAchizitieProdus();
    testAdaugaMonedeValide();
    testAdaugaMonedeInvalide();
    testSumaTotalaInTonomat();
    testValidator();
    cout<<"Toate testele realizate au trecut cu succes!"<<endl;
}
