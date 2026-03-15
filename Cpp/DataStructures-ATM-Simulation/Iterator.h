#ifndef ITERATOR_H_INCLUDED
#define ITERATOR_H_INCLUDED

#include "Multime.h"

template<typename T>
class Iterator {
private:
    const Multime<T>& multime;
    int position;

public:
    Iterator(const Multime<T>& m);
    void first();
    void next();
    bool valid() const;
    T current() const;
};

#include "Iterator.tpp"

#endif // ITERATOR_H_INCLUDED
