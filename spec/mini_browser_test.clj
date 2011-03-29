(ns solutions.mini-browser-test
  (:use circumspec solutions.mini-browser))

(testing-fn namespace-link
  ("foo" -> [:a {:href "/browse/foo"} "foo"]))

(testing-fn namespace-browser
  (["foo", "bar"] -> [:div
                      {:class "browse-list"}
                      [:ul
                       [[:li [:a {:href "/browse/foo"} "foo"]]
                        [:li [:a {:href "/browse/bar"} "bar"]]]]]))

(testing-fn var-link
  ("com.foo" "zap" -> [:a {:href "/browse/com.foo/zap"} "zap"])
  ("com.foo" "zap?" -> [:a {:href "/browse/com.foo/zap%3F"} "zap?"]))


