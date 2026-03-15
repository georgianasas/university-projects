
template<class T>
Iterator<T>::Iterator(const Multime<T>& m) : multime(m), position(0) {}

template <class T>
void Iterator<T>::first() {
    position = 0;
}


template <class T>
void Iterator<T>::next() {
    if (valid()) {
        position++;
    }
}

template <class T>
bool Iterator<T>::valid() const {
    return (0 <= position && position < multime.size());
}

template <class T>
T Iterator<T>::current() const {
    return multime.getElem(position);
}
