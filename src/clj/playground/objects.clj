(ns playground.objects)

(comment "Goal"
  (defclass Person
    (method say-hello []
            (str "Hello, my name is " name)))

  (def p (Person :new {:name "Miguel"}))
  (p :say-hello))

(defn new-class
  "Class constructor"
  [name & specs]
  {:name name
   :methods specs})

(new-class
 'Person
 '(method say-hello []
         (str "Hello, my name is " name)))

(defn new-object
  [klass]
  (fn [call]
    (condp = call
      :class klass)))

(def instance (new-object 'Person))
(instance :class)
