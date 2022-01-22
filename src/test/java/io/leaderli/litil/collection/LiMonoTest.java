package io.leaderli.litil.collection;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;
import java.util.function.Function;

@SuppressWarnings({"rawtypes", "unchecked"})
public class LiMonoTest extends Assert {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test1() {


        Data data = new Data();
        thrown.expect(NullPointerException.class);
        @SuppressWarnings("unused")
        String name = data.getBody().getRequest().getName();

    }

    @Test
    public void test() {


        Data data = new Data();

        LiMoNo.of(data)
                .map(Data::getBody)
                .map(Body::getRequest)
                .map(Request::getName)
                .then(name -> assertEquals("hello", name));

        Body body = new Body();
        Request request = new Request();
        request.setName("hello");
        body.setRequest(request);
        data.setBody(body);

        LiMoNo.of(data)
                .map(Data::getBody)
                .map(Body::getRequest)
                .map(Request::getName)
                .then(name -> assertEquals("hello", name))
                .error(() -> System.out.println("there is something error when get name"));

    }

    @Test
    public void test3() {
        Data data = new Data();

        LiMoNo<Body> mono = LiMoNo.of(data)
                .map(Data::getBody);
        assertTrue(mono.notPresent());
        LiMoNo<String> name = mono
                .map(Body::getRequest)
                .map(Request::getName)
                .or("123");

        assertTrue(!name.isPresent() || "123".equals(name.get()));

    }

    @Test
    public void cast1() {

        Object obj = new HashMap<>();

        LiMoNo.of(obj)
                .cast(Map.class)
                .then(map -> assertEquals(0, map.size()))
                .cast(List.class)
                .then(list -> {

                });

    }

    @Test
    public void test5() {

        List list = new ArrayList<>();

        list.add("1");
        list.add("2");
        list.add(1);

        LiFlux<String> flux = LiMoNo.of(list).flux(String.class);
        assertEquals(2, flux.size());

        LiFlux<Integer> intArr = LiMoNo.of(list).flux(int.class);
        LiFlux<Integer> intArr2 = LiMoNo.of(list).flux(Integer.class);

        assertEquals(1, intArr.size());
        assertEquals(1, intArr2.size());

        Map map = new HashMap<>();

        map.put("1", "1");
        map.put("2", 2);
        map.put(3, 3);


        Map<String, String> stringMap = LiMoNo.of(map).cast(String.class, String.class).get();

        assertEquals(1, stringMap.size());

    }

    @Test
    public void test6() {
        List list = new ArrayList<>();

        list.add("1");
        list.add("2");
        list.add(1);

        List<LiMoNo<String>> stream = LiMoNo.of(list).flux(String.class).getMonoCopy();

        assertEquals(2, stream.size());


        list = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("m1", "1");
        map1.put("m2", 2);

        list.add(map1);
        list.add("1");
        list.add("2");
        list.add(1);
        assertEquals(1, LiMoNo.of(list).flux(Map.class).size());

        LiFlux<Map<String, String>> mapStream = LiMoNo.of(list).flux(Map.class).cast(String.class, String.class);
        assertEquals(1, mapStream.size());


    }


    @Test
    public void filter() {
        LiMoNo<String> mono = LiMoNo.of(null);

        Assert.assertNull(mono.filter(Objects::nonNull).get());
        Assert.assertNull(mono.filter(str -> str.length() == 4).get());

        mono = LiMoNo.of("123");
        Assert.assertNotNull(mono.filter(Objects::nonNull).get());
        Assert.assertNull(mono.filter(str -> str.length() == 4).get());

//
        mono = LiMoNo.of("123");
        Assert.assertNotNull(mono.filter(LiMoNo::of).get());

        Assert.assertNull(mono.cast(int.class).cast(String.class).get());
        Assert.assertNull(mono.filter(it -> LiMoNo.of(it).cast(int.class).cast(String.class)).get());

        Assert.assertNull(mono.filter(it -> null).get());
        Assert.assertNotNull(mono.filter(it -> 1).get());

        Assert.assertNull(mono.filter(it -> LiFlux.empty()).get());
        Assert.assertNotNull(mono.filter(it -> LiFlux.of(1)).get());

        Assert.assertNull(mono.filter(it -> new ArrayList<>()).get());
        Assert.assertNotNull(mono.filter(it -> Arrays.asList(1, 2)).get());

        HashMap<Object, Object> test = new HashMap<>();
        Assert.assertNull(mono.filter(it -> test).get());
        test.put("key", "value");
        Assert.assertNotNull(mono.filter(it -> test).get());
//        LiMono<String> filter = LiMono.of(null);
//        LiMono<Boolean> cast = filter.cast(Boolean.class);
//        System.out.println(cast);
//        LiMono<Boolean> or = cast
//                .or(filter.cast(LiMono.class).isPresent());
//        System.out.println(or);
//        LiMono<Boolean> or1 = or
//                .or(filter.cast_map(LiFlux.class, LiFlux::notEmpty));
//        System.out.println(or1);
//        System.out.println(or1.getOrOther(false));

    }

    @Test
    public void getOr() {

        String or = LiMoNo.<String>of(null).getOrOther("123");
        Assert.assertEquals("123", or);
    }

    @Test
    public void testCast() {

        Assert.assertTrue(LiMoNo.of("123").cast(CharSequence.class).isPresent());
    }

    @Test
    public void generic() {

        LiMoNo<Integer> length = LiMoNo.of("123").map(CharSequence::length);
        Assert.assertEquals(3, length.get().intValue());


        ArrayList<Object> list = new ArrayList<>();
        LiMoNo<List<Object>> result = LiMoNo.of(list).map(l -> l);

        Assert.assertTrue(result.isPresent());

        Map<String, String> map = new HashMap<>();
        map.put("1", "1");

        LiMoNo<Object> mapMono = LiMoNo.of(map);

        mapMono.cast_map(String.class, String.class, (Function<Map<String, String>, CharSequence>) Object::toString);
    }

    private static class Data {

        private Body body;

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }
    }

    private static class Body {

        private Request request;

        public Request getRequest() {
            return request;
        }

        public void setRequest(Request request) {
            this.request = request;
        }
    }

    private static class Request {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public <T, R> R of(T t, Function<? super T, ? extends R> mapping) {
        LiMoNo<? extends R> map = LiMoNo.of(t).map(mapping);
        return map.get();
    }

    @Test
    public void testCase() {

        LiCase<Object, Integer> liCase = LiMoNo.<Object>of("1").toCase();

        liCase.case_map(Integer.class, s -> s);
        liCase.case_map(String.class, String::length);

        LiMoNo<Integer> of = liCase.mono();
        Assert.assertEquals(1, of.get().intValue());

        liCase = LiMoNo.<Object>of(100).toCase();

        liCase.case_map(Integer.class, s -> s);
        liCase.case_map(String.class, CharSequence::length);

        of = liCase.mono();
        Assert.assertEquals(100, of.get().intValue());

    }

    @Test
    public void testFlux() {

        Assert.assertEquals(3, LiMoNo.of(Arrays.asList(1, 2, 3)).fluxByArray(List::toArray).size());
        Assert.assertEquals(3, LiMoNo.of(Arrays.asList(1, 2, 3)).flux(Integer.class).size());
        Assert.assertEquals(3, LiMoNo.of(Arrays.asList(1, 2, 3)).flux(list -> list).size());
        Assert.assertEquals(3, LiMoNo.of(Arrays.asList(1, 2, 3)).fluxByIterator(List::iterator).size());

    }
}
