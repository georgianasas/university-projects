#ifndef EXCEPTII_H
#define EXCEPTII_H
#include<stdexcept>
#include<string>

class ExceptieProdus:public std::runtime_error{
  public:
    explicit ExceptieProdus(const std::string&mesaj):std::runtime_error(mesaj){}
};

class ExceptieRepo:public std::runtime_error{
  public:
    explicit ExceptieRepo(const std::string&mesaj):std::runtime_error(mesaj){}
};

class ExceptieTonomat:public std::runtime_error{
  public:
    explicit ExceptieTonomat(const std::string& mesaj):std::runtime_error(mesaj){}
};


#endif
