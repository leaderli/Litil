package io.leaderli.litil.collection;

import io.leaderli.litil.meta.LiMeta;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class LiFluxTest {


    @Test
    public void of() {

        Assert.assertTrue(LiFlux.empty().isEmpty());
        Assert.assertTrue(LiFlux.of(1).notEmpty());


        Assert.assertTrue(LiFlux.of(new ArrayList<>()).isEmpty());
        assertEquals(2, LiFlux.of(Arrays.asList(1, 2)).size());

        Iterator<Integer> iterator = Arrays.asList(1, 2, 3).iterator();

        assertEquals(3, LiFlux.of(iterator).size());

    }

    @Test
    public void get() {

        LiFlux<Integer> flux = LiFlux.of(1, 2, 3);
        assertEquals(3, flux.size());

        List<LiMoNo<Integer>> liNos = flux.getMonoCopy();
        liNos.add(LiMoNo.of(4));

        assertEquals(4, liNos.size());
        assertEquals(3, flux.size());

    }

    @Test
    public void getOr() {
        List<LiMoNo<Integer>> flux = LiFlux.<Integer>empty().getMonoCopyOrOther(1, 2, 3);
        assertEquals(3, flux.size());
        flux = LiFlux.of(1).getMonoCopyOrOther(1, 2, 3);
        assertEquals(1, flux.size());

        LiFlux<Object> empty = LiFlux.empty();
        assertEquals(0, empty.size());
        assertEquals(1, empty.or(1).size());
        assertEquals(1, empty.getMonoCopyOrOther(1).size());
        assertEquals(0, empty.size());
    }

    @Test
    public void or() {
        LiFlux<Integer> flux = LiFlux.<Integer>empty().or(1, 2, 3);
        assertEquals(3, flux.size());

        flux = LiFlux.of(1).or(1, 2, 3);
        assertEquals(1, flux.size());

    }

    @Test
    public void cast() {

        List<Object> objs = Arrays.asList(1, 2, 3);
        LiFlux<Object> of = LiFlux.of(objs);

        LiFlux<Integer> cast = of.cast(Integer.class);

        assertEquals(3, cast.size());


        assertEquals("1", of.cast_map(Integer.class, String::valueOf).getFirst().get());

    }

    @Test
    public void mapCast() {
        ArrayList<Object> list = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("m1", "1");
        map1.put("m2", 2);

        list.add(map1);
        list.add("1");
        list.add("2");
        list.add(1);

        LiFlux<Map<String, String>> mono = LiMoNo.of(list).flux(Map.class).cast(String.class, String.class);

        assertEquals(1, mono.size());

        LiFlux<Integer> integerLiFlux = LiMoNo.of(list).flux(Map.class).cast_map(String.class, Object.class, Map::size);
        assertEquals(2, integerLiFlux.getFirst().get().intValue());

    }

    @Test
    public void trim() {
        LiFlux<Integer> flux = LiFlux.of(1, null, 2);

        assertEquals(3, flux.size());
        flux.trim();
        assertEquals(3, flux.size());

        flux = flux.trim();
        assertEquals(2, flux.size());

    }

    @Test
    public void filter() {
        LiFlux<Integer> flux = LiFlux.of(1, null, 2);

        assertEquals(3, flux.size());
        flux.filter(null);
        assertEquals(3, flux.size());

        flux = flux.filter(null);
        assertEquals(2, flux.size());

        flux = flux.filter(it -> it > 1);
        assertEquals(1, flux.size());

        Assert.assertTrue(flux.or(1, 2, 3).filter(it -> it > 1).getFirst().get() == 2);

    }

    @Test
    public void remove() {
        LiFlux<Integer> flux = LiFlux.of(1, null, 2);
        assertEquals(3, flux.size());

        flux.remove(1);
        assertEquals(2, flux.size());

        flux.remove(null);
        assertEquals(1, flux.size());
    }

    @Test
    public void getRawList() {
        LiFlux<Integer> flux = LiFlux.of(1, null, 2);

        assertEquals(2, flux.getRawCopy().size());

        LiMeta<Integer> count = new LiMeta<>(0);
        flux.forEach(in -> count.set(count.get() + 1));
        assertEquals(2, count.get().intValue());
    }

    @Test
    public void getFirst() {
        LiFlux<Integer> flux = LiFlux.of(1, null, 2);

        Assert.assertEquals((Integer) 1, flux.getFirst().get());

        Assert.assertEquals((Integer) 2, flux.getFirst(it -> it > 1).get());

    }

    @SuppressWarnings("all")
    @Test
    public void append() {
        LiFlux<Integer> append = LiFlux.<Integer>empty().add(1);
        assertEquals(1, append.size());
        Iterator a = null;
        append.add(a);
        assertEquals(1, append.size());

    }
}
