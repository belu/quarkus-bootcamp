package ch.open.messaging;

import ch.open.dto.NewFact;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class NewFactProducer {

    public static final String FACTS_OUT = "facts-out";

    @Inject
    @Channel(FACTS_OUT)
    Emitter<NewFact> factsEmitter;

    @Scheduled(every = "5s", delay = 3, delayUnit = TimeUnit.SECONDS)
    public void produceNewFact() {
        factsEmitter.send(randomFact());
    }

    private final Random random = new Random();

    private NewFact randomFact() {
        return new NewFact(FACTS.get(random.nextInt(FACTS.size())));
    }

    private static final List<String> FACTS = List.of(
        "Back when dinosaurs existed, there used to be volcanoes that were erupting on the moon.",
        "The only letter that doesn't appear on the periodic table is J.",
        "A single strand of Spaghetti is called a 'Spaghetto'.",
        "The first movie ever to put out a motion-picture soundtrack was Snow White and the Seven Dwarves.",
        "At birth, a baby panda is smaller than a mouse.",
        "Iceland does not have a railway system.",
        "The largest known prime number has 17,425,170 digits. The new prime number is 2 multiplied by itself 57,885,161 times, minus 1.",
        "The tongue is the only muscle in one's body that is attached from one end.",
        "The Lego Group is the world's most powerful brand. There are more Lego Minifigures than there are people on Earth.",
        "The Bagheera kiplingi spider was discovered in the 1800s and is the only species of spider that has been classified as vegetarian.",
        "There is a boss in that can be defeated by not playing the game for a week; or by changing the date.",
        "The Roman - Persian wars are the longest in history, lasting over 680 years. They began in 54 BC and ended in 628 AD.",
        "An 11-year-old girl proposed the name for Pluto after the Roman god of the Underworld.",
        "The voice actor of SpongeBob and the voice actor of Karen, Plankton's computer wife, have been married since 1995.",
        "An estimated 50% of all gold ever mined on Earth came from a single plateau in South Africa: Witwatersrand.",
        "75% of the world's diet is produced from just 12 plant and five different animal species.",
        "The largest Japanese population outside of Japan stands at 1.6 million people who live in Brazil.",
        "Violin bows are commonly made from horse hair.",
        "There are less than 30 ships in the Royal Canadian Navy which are less than most third-world countries.",
        "Costa Coffee employs Gennaro Pelliccia as a coffee taster, who has had his tongue insured for £10 million since 2009.",
        "People who post their fitness routine on Facebook are more likely to have psychological problems.",
        "Medieval chastity belts are a myth. A great majority of examples now existing were made in the 18th and 19th centuries as jokes.",
        "Standing around burns calories. On average, a 150-pound person burns 114 calories per hour while standing and doing nothing.",
        "Although GPS is free for the world to use, it costs $2 million per day to operate. The money comes from American tax revenue.",
        "In World War II, Germany tried to collapse the British economy by dropping millions of counterfeit bills over London.",
        "The color red doesn't really make bulls angry; they are color-blind.",
        "65% of autistic kids are left-handed, and only 10% of people, in general, are left-handed.",
        "Lettuce is a member of the sunflower family.",
        "The Hobbit has been published in two editions. In the first edition, Gollum willingly bet on his ring in the riddle game.",
        "Swedish meatballs originated from a recipe King Charles XII brought back from Turkey in the early 1800s.",
        "There are times when Pluto is closer to the Sun than Neptune - one of these timelines was from 1979 to 1999.",
        "The Ethiopian calendar is 7.5 years behind the Gregorian calendar due to the fact that it has 13 months."
    );
}
