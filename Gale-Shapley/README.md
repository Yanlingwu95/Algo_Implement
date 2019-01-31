Gale-Shapley Algorithm is designed to address the   [Stable Marriage Problem](http://en.wikipedia.org/wiki/Stable_marriage_problem) and get the perfect stable matching pairs.  My implementation of Gale-Shapley algorithm is in Java. 

---

### Pseudocode

---

Initially all m ∈ M and w ∈W are free
While there is a man m who is free and hasn’t proposed to every woman
​	Choose such a man m
​	Let w be the highest-ranked woman in m’s preference list to whom m has not yet proposed
​	If w is free then
​		(m, w) become engaged
​	Else w is currently engaged to m'
​		If w prefers m' to m then
​			m remains free
​		Else w prefers m to m'
​			(m, w) become engaged
​			m' becomes free
​		Endif
​	Endif
Endwhile
Return the set S of engaged pairs

---

### Data Structure

---

1. Consider selecting a free man. Do this by maintaining the set of free as linked list. 
2. Consider a man m. We need to identify the highest-ranked woman to whom he has not yet proposed.  Use array next[n] to indicates for each man m the position of the next woman he will propose to on his list.
3. If a man m proposes to woman w; we need to be able to identify the man m' that w is engaged to. Use array current[n] to indicate this. current[w] is the woman w’s current partner m'. 
4. This one is a little tricky. we create an n × n array Ranking, where
   Ranking[w,m] contains the rank of man m in the sorted order of w’s preferences. By this single array, we can compare the preference m with m' for a woman in constant time. 

---

### Time complexity --- O(n^2)

---

## See Also

- [Chapter 1](https://github.com/joyrexus/algo-design/tree/master/ch-01) of *Algorithm Design* by Kleinberg and Tardos.
- [Minor variant](https://github.com/joyrexus/algo-design/tree/master/ch-01/solved/ex-2) where certain marriages are forbidden.



