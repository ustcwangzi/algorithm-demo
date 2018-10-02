/**
 * <p>Title: DogCatQueue</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/10/2</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.stackandqueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>猫狗队列</p>
 * <p>
 *     要求：
 *          1、add()将cat或dog放入队列
 *          2、pollAll()将队列中所有实例按照进队的先后顺序依次出队
 *          3、pollDog()或pollCat()将队列中对应的dog／cat实例按照进队的先后顺序依次出队
 *     错误的解决方案：
 *          cat队列只放cat实例，dog队列只放dog实例，总队列放所用实例
 *          这种设计会导致cat、dog以及总队列的更新有问题，例如弹出一个cat时，总队列中的cat不一定在队首
 *     解决方案：
 *          自定义PetEnterQueue，pet是原实例，count是这个实例的时间戳
 *          用一个不断累加的count来表示实例进队时间，同时有两个队列cat和dog保存各自的实例
 *          进队时，加上时间戳，pollAll()时根据队首count的大小决定从哪个队列进行出队
 * </p>
 *
 * @author wangzi
 */
public class DogCatQueue {
    private static final String TYPE_DOG = "dog";
    private static final String TYPE_CAT = "cat";

    public static class Pet {
        private String type;

        public Pet(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public static class Dog extends Pet {
        public Dog() {
            super(TYPE_DOG);
        }
    }

    public static class Cat extends Pet {
        public Cat() {
            super(TYPE_CAT);
        }
    }

    /**
     * 自定义一个新类PetEnterQueue
     * pet是原实例，count是这个实例的时间戳
     */
    public static class PetEnterQueue {
        private Pet pet;
        private long count;

        public PetEnterQueue(Pet pet, long count) {
            this.pet = pet;
            this.count = count;
        }

        public Pet getPet() {
            return pet;
        }

        public long getCount() {
            return count;
        }

        public String getPetType() {
            return pet.getType();
        }
    }

    /**
     * 实现猫狗队列
     */
    public static class DogCatQueueImpl {
        private Queue<PetEnterQueue> dogQueue;
        private Queue<PetEnterQueue> catQueue;
        private long count;

        public DogCatQueueImpl() {
            this.dogQueue = new LinkedList<>();
            this.catQueue = new LinkedList<>();
            this.count = 0;
        }

        public void add(Pet pet) {
            if (pet.getType().equals(TYPE_DOG)) {
                dogQueue.add(new PetEnterQueue(pet, count++));
            } else if (pet.getType().equals(TYPE_CAT)) {
                catQueue.add(new PetEnterQueue(pet, count++));
            } else {
                throw new RuntimeException("err, not dog or cat");
            }
        }

        public Pet pollAll() {
            if (!dogQueue.isEmpty() && !catQueue.isEmpty()) {
                if (dogQueue.peek().getCount() < catQueue.peek().count) {
                    return dogQueue.poll().getPet();
                } else {
                    return catQueue.poll().getPet();
                }
            } else if (!dogQueue.isEmpty()) {
                return dogQueue.poll().getPet();
            } else if (!catQueue.isEmpty()) {
                return catQueue.poll().getPet();
            } else {
                throw new RuntimeException("err, queue is empty!");
            }
        }

        public Dog pollDog() {
            if (dogQueue.isEmpty()) {
                throw new RuntimeException("Dog queue is empty!");
            }
            return (Dog) dogQueue.poll().getPet();
        }

        public Cat pollCat() {
            if (catQueue.isEmpty()) {
                throw new RuntimeException("Cat queue is empty!");
            }
            return (Cat) catQueue.poll().getPet();
        }

        public boolean isEmpty() {
            return dogQueue.isEmpty() && catQueue.isEmpty();
        }

        public boolean isDogQueueEmpty() {
            return dogQueue.isEmpty();
        }

        public boolean isCatQueueEmpty() {
            return catQueue.isEmpty();
        }

        public static void main(String[] args) {
            DogCatQueueImpl dogCatQueue = new DogCatQueueImpl();

            Pet dog1 = new Dog();
            Pet cat1 = new Cat();
            Pet dog2 = new Dog();
            Pet cat2 = new Cat();
            Pet dog3 = new Dog();
            Pet cat3 = new Cat();

            dogCatQueue.add(dog1);
            dogCatQueue.add(cat1);
            dogCatQueue.add(dog2);
            dogCatQueue.add(cat2);
            dogCatQueue.add(dog3);
            dogCatQueue.add(cat3);

            dogCatQueue.add(dog1);
            dogCatQueue.add(cat1);
            dogCatQueue.add(dog2);
            dogCatQueue.add(cat2);
            dogCatQueue.add(dog3);
            dogCatQueue.add(cat3);

            dogCatQueue.add(dog1);
            dogCatQueue.add(cat1);
            dogCatQueue.add(dog2);
            dogCatQueue.add(cat2);
            dogCatQueue.add(dog3);
            dogCatQueue.add(cat3);

            while (!dogCatQueue.isDogQueueEmpty()) {
                System.out.println(dogCatQueue.pollDog().getType());
            }

            while (!dogCatQueue.isEmpty()) {
                System.out.println(dogCatQueue.pollAll().getType());
            }
        }
    }
}
