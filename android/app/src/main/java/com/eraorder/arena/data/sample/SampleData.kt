package com.eraorder.arena.data.sample

import com.eraorder.arena.domain.model.Arena
import com.eraorder.arena.domain.model.CausePair
import com.eraorder.arena.domain.model.HistoryEvent
import com.eraorder.arena.domain.model.Region

object SampleData {

    const val ARENA_ANCIENT = "ancient"
    const val ARENA_EXPLORATION = "exploration"
    const val ARENA_SCIENCE = "science"
    const val ARENA_REVOLUTIONS = "revolutions"
    const val ARENA_EMPIRES = "empires"
    const val ARENA_MODERN = "modern"

    val arenas: List<Arena> = listOf(
        Arena(
            id = ARENA_ANCIENT,
            name = "ANCIENT WORLDS",
            eraRange = "3200 BCE - 476 CE",
            spriteKey = "sprite_1",
            primer = "Clay tablets, pyramids and the first written laws. Order the moments that turned scattered settlements into the earliest civilisations.",
            eventCount = 12
        ),
        Arena(
            id = ARENA_EXPLORATION,
            name = "EXPLORATION",
            eraRange = "1000 - 1699",
            spriteKey = "sprite_2",
            primer = "Seven centuries of ships, caravans and hand-drawn charts. Place the voyages that stitched distant coastlines into one map.",
            eventCount = 12
        ),
        Arena(
            id = ARENA_SCIENCE,
            name = "SCIENCE",
            eraRange = "820 - 1952",
            spriteKey = "sprite_3",
            primer = "From algebra in Baghdad to the photograph that revealed DNA. Order the discoveries that rebuilt how the world explains itself.",
            eventCount = 12
        ),
        Arena(
            id = ARENA_REVOLUTIONS,
            name = "REVOLUTIONS",
            eraRange = "1688 - 1917",
            spriteKey = "sprite_4",
            primer = "Crowds, constitutions and the fall of old certainties. Slot each uprising into the decade where it actually happened.",
            eventCount = 12
        ),
        Arena(
            id = ARENA_EMPIRES,
            name = "EMPIRES AND FALLS",
            eraRange = "500 BCE - 1922",
            spriteKey = "sprite_4",
            primer = "Every empire in this arena rose and then ran out of time. Order the high points and the collapses that followed them.",
            eventCount = 12
        ),
        Arena(
            id = ARENA_MODERN,
            name = "MODERN AGE",
            eraRange = "1903 - 1994",
            spriteKey = "sprite_5",
            primer = "One short century of flight, fission, orbit and the fall of walls. Place the events that still shape the morning news.",
            eventCount = 12
        )
    )

    val events: List<HistoryEvent> = listOf(
        HistoryEvent(
            "anc01", ARENA_ANCIENT, "Cuneiform writing appears in Uruk", -3200, "Uruk", Region.ASIA,
            "Sumerian scribes pressed wedge marks into wet clay to track grain and cattle. Those receipts became the oldest writing system known."
        ),
        HistoryEvent(
            "anc02", ARENA_ANCIENT, "Great Pyramid of Giza completed", -2560, "Giza", Region.AFRICA,
            "Khufu was buried under roughly two million limestone blocks cut and hauled by rotating work gangs. The pyramid stayed the tallest structure on Earth for close to four thousand years."
        ),
        HistoryEvent(
            "anc03", ARENA_ANCIENT, "Code of Hammurabi carved in stone", -1754, "Babylon", Region.ASIA,
            "Hammurabi had 282 laws cut into a basalt pillar and set up where anyone could see them. Publishing the penalties in advance was the novelty, not their severity."
        ),
        HistoryEvent(
            "anc04", ARENA_ANCIENT, "Tutankhamun buried in the Valley of the Kings", -1323, "Thebes", Region.AFRICA,
            "A minor pharaoh died young and was sealed into a cramped, hurriedly decorated tomb. Its obscurity is exactly why robbers missed it and archaeologists did not."
        ),
        HistoryEvent(
            "anc05", ARENA_ANCIENT, "First recorded Olympic Games", -776, "Olympia", Region.EUROPE,
            "The games began as a single foot race run during a religious festival for Zeus. Greek cities later counted years by the four-year gaps between them."
        ),
        HistoryEvent(
            "anc06", ARENA_ANCIENT, "Traditional founding of Rome", -753, "Rome", Region.EUROPE,
            "Roman historians fixed this date for the settlement on the Palatine Hill. Archaeology shows huts on the site rather than a city, but the date stuck."
        ),
        HistoryEvent(
            "anc07", ARENA_ANCIENT, "Cyrus founds the Persian Empire", -550, "Pasargadae", Region.ASIA,
            "Cyrus overthrew the Median king and built the largest empire the world had yet seen. He ruled conquered peoples through their own customs instead of replacing them."
        ),
        HistoryEvent(
            "anc08", ARENA_ANCIENT, "Battle of Marathon", -490, "Marathon", Region.EUROPE,
            "An outnumbered Athenian force broke a Persian landing on the coast of Attica. The victory bought Greece a decade to prepare for the invasion that followed."
        ),
        HistoryEvent(
            "anc09", ARENA_ANCIENT, "Ashoka carves his rock edicts", -260, "Pataliputra", Region.ASIA,
            "After a war he regretted, the Mauryan emperor had moral instructions carved on rocks and pillars across his realm. They are among the earliest datable texts in South Asia."
        ),
        HistoryEvent(
            "anc10", ARENA_ANCIENT, "Caesar crosses the Rubicon", -49, "Ravenna", Region.EUROPE,
            "Leading a legion across the border river was an act of war against the Roman Senate. The civil war it started ended the Republic within a generation."
        ),
        HistoryEvent(
            "anc11", ARENA_ANCIENT, "Vesuvius buries Pompeii", 79, "Pompeii", Region.EUROPE,
            "Ash and pumice sealed a working Roman town in a single afternoon. The result is the most complete picture of ordinary Roman life anywhere."
        ),
        HistoryEvent(
            "anc12", ARENA_ANCIENT, "Fall of the Western Roman Empire", 476, "Rome", Region.EUROPE,
            "Odoacer deposed the last western emperor and sent the imperial regalia to Constantinople. No single battle ended Rome; the office simply stopped being filled."
        ),
        HistoryEvent(
            "exp01", ARENA_EXPLORATION, "Leif Erikson reaches Vinland", 1000, "Newfoundland", Region.AMERICAS,
            "Norse sailors from Greenland built a short-lived camp on the North American coast. The turf houses they left behind confirm the sagas five centuries before Columbus."
        ),
        HistoryEvent(
            "exp02", ARENA_EXPLORATION, "Marco Polo sets out for Asia", 1271, "Venice", Region.EUROPE,
            "A teenage merchant left Venice with his father and uncle for the court of Kublai Khan. The account he dictated in prison shaped European ideas of Asia for centuries."
        ),
        HistoryEvent(
            "exp03", ARENA_EXPLORATION, "Ibn Battuta begins his travels", 1325, "Tangier", Region.AFRICA,
            "A young scholar left Morocco for Mecca and kept going for nearly thirty years. He covered more ground than any traveller before the age of steam."
        ),
        HistoryEvent(
            "exp04", ARENA_EXPLORATION, "First Ming treasure voyage sails", 1405, "Nanjing", Region.ASIA,
            "A fleet of hundreds of ships under Zheng He sailed for Southeast Asia and the Indian Ocean. The largest vessels dwarfed anything European yards could build."
        ),
        HistoryEvent(
            "exp05", ARENA_EXPLORATION, "Dias rounds the Cape of Good Hope", 1488, "Cape Town", Region.AFRICA,
            "A storm pushed his ships past the southern tip of Africa before the crew realised it. The route to the Indian Ocean was suddenly open."
        ),
        HistoryEvent(
            "exp06", ARENA_EXPLORATION, "Columbus reaches the Bahamas", 1492, "San Salvador", Region.AMERICAS,
            "Looking for a westward route to Asia, he made landfall on a Caribbean island. He died still insisting he had reached the edge of the Indies."
        ),
        HistoryEvent(
            "exp07", ARENA_EXPLORATION, "Treaty of Tordesillas signed", 1494, "Tordesillas", Region.EUROPE,
            "Spain and Portugal drew a line down the Atlantic and split the unmapped world between them. Nobody consulted the people already living on either side of it."
        ),
        HistoryEvent(
            "exp08", ARENA_EXPLORATION, "Da Gama reaches India by sea", 1498, "Calicut", Region.ASIA,
            "The first all-water route from Europe to India broke the overland spice monopoly. Portuguese trading posts followed within a decade."
        ),
        HistoryEvent(
            "exp09", ARENA_EXPLORATION, "Magellan expedition returns to Seville", 1522, "Seville", Region.EUROPE,
            "Eighteen survivors of an original two hundred and seventy completed the first circumnavigation. Magellan himself had died in the Philippines a year earlier."
        ),
        HistoryEvent(
            "exp10", ARENA_EXPLORATION, "Mercator publishes his world map", 1569, "Duisburg", Region.EUROPE,
            "His projection let sailors draw a constant compass bearing as a straight line. It distorts area badly, which is the price of that one useful property."
        ),
        HistoryEvent(
            "exp11", ARENA_EXPLORATION, "Tasman charts New Zealand", 1642, "Golden Bay", Region.OCEANIA,
            "A Dutch expedition sighted the South Island and mapped part of its coast. Tasman never realised he had sailed around the whole Australian continent."
        ),
        HistoryEvent(
            "exp12", ARENA_EXPLORATION, "Dampier maps the Australian coast", 1699, "Shark Bay", Region.OCEANIA,
            "An English buccaneer turned naturalist recorded plants, winds and currents of New Holland. His published journals later guided Cook."
        ),
        HistoryEvent(
            "sci01", ARENA_SCIENCE, "Al-Khwarizmi writes his algebra treatise", 820, "Baghdad", Region.ASIA,
            "His book on balancing and restoring equations gave algebra both its method and its name. A Latin version of his own name became the word algorithm."
        ),
        HistoryEvent(
            "sci02", ARENA_SCIENCE, "Gutenberg prints with movable type", 1440, "Mainz", Region.EUROPE,
            "Reusable metal letters and an adapted wine press made books cheap to copy. Within fifty years European presses had produced millions of volumes."
        ),
        HistoryEvent(
            "sci03", ARENA_SCIENCE, "Copernicus publishes the heliocentric model", 1543, "Frombork", Region.EUROPE,
            "He placed the Sun rather than the Earth at the centre of the planetary system. The book appeared in the year he died, which spared him the argument."
        ),
        HistoryEvent(
            "sci04", ARENA_SCIENCE, "Galileo turns a telescope on Jupiter", 1610, "Padua", Region.EUROPE,
            "Four moons circling another planet showed that not everything orbits the Earth. He published the observations within weeks of making them."
        ),
        HistoryEvent(
            "sci05", ARENA_SCIENCE, "Newton publishes the Principia", 1687, "Cambridge", Region.EUROPE,
            "Three laws of motion and one of gravitation explained falling apples and orbiting moons together. It set the template for mathematical physics."
        ),
        HistoryEvent(
            "sci06", ARENA_SCIENCE, "Jenner tests the first vaccine", 1796, "Berkeley", Region.EUROPE,
            "He used material from cowpox sores to protect a boy against smallpox. The Latin word for cow gave the procedure its name."
        ),
        HistoryEvent(
            "sci07", ARENA_SCIENCE, "Faraday demonstrates induction", 1831, "London", Region.EUROPE,
            "Moving a magnet through a coil produced a current with no battery involved. Every generator and transformer since runs on that single observation."
        ),
        HistoryEvent(
            "sci08", ARENA_SCIENCE, "Darwin publishes On the Origin of Species", 1859, "London", Region.EUROPE,
            "Twenty years of notes finally went to print once a rival reached the same idea. The first run sold out on the day of publication."
        ),
        HistoryEvent(
            "sci09", ARENA_SCIENCE, "Mendeleev orders the periodic table", 1869, "Saint Petersburg", Region.EUROPE,
            "He arranged the known elements by weight and left gaps where the pattern demanded them. The elements that later filled those gaps matched his predictions."
        ),
        HistoryEvent(
            "sci10", ARENA_SCIENCE, "Curie isolates radium", 1898, "Paris", Region.EUROPE,
            "Marie and Pierre Curie processed tonnes of pitchblende to extract a fraction of a gram. The work earned her the first of two Nobel Prizes."
        ),
        HistoryEvent(
            "sci11", ARENA_SCIENCE, "Einstein publishes general relativity", 1915, "Berlin", Region.EUROPE,
            "Gravity became the curvature of spacetime rather than a force acting at a distance. A solar eclipse four years later confirmed the bending of starlight."
        ),
        HistoryEvent(
            "sci12", ARENA_SCIENCE, "Franklin photographs DNA fibres", 1952, "London", Region.EUROPE,
            "Her X-ray image showed the unmistakable cross pattern of a helix. It was the decisive clue to the structure of the molecule."
        ),
        HistoryEvent(
            "rev01", ARENA_REVOLUTIONS, "Glorious Revolution in England", 1688, "London", Region.EUROPE,
            "Parliament invited William of Orange to replace James II with almost no fighting. The Bill of Rights that followed put the crown under law."
        ),
        HistoryEvent(
            "rev02", ARENA_REVOLUTIONS, "Declaration of Independence signed", 1776, "Philadelphia", Region.AMERICAS,
            "Thirteen colonies declared themselves free of British rule and explained why in public. The war to make the claim true lasted another seven years."
        ),
        HistoryEvent(
            "rev03", ARENA_REVOLUTIONS, "Storming of the Bastille", 1789, "Paris", Region.EUROPE,
            "A Paris crowd took a royal fortress holding only seven prisoners. The symbol mattered far more than the prize."
        ),
        HistoryEvent(
            "rev04", ARENA_REVOLUTIONS, "Haitian revolution begins", 1791, "Saint-Domingue", Region.AMERICAS,
            "Enslaved workers rose against the richest colony in the Caribbean. Thirteen years later they had founded the first free black republic."
        ),
        HistoryEvent(
            "rev05", ARENA_REVOLUTIONS, "Bolivar delivers the Angostura address", 1819, "Angostura", Region.AMERICAS,
            "He laid out a constitution for a republic that did not yet exist. Within months his army had crossed the Andes and won it."
        ),
        HistoryEvent(
            "rev06", ARENA_REVOLUTIONS, "Year of revolutions across Europe", 1848, "Paris", Region.EUROPE,
            "Uprisings broke out in more than fifty countries within a few months. Almost all were crushed, and almost all left reforms behind."
        ),
        HistoryEvent(
            "rev07", ARENA_REVOLUTIONS, "Serfdom abolished in Russia", 1861, "Saint Petersburg", Region.EUROPE,
            "Alexander II freed more than twenty million peasants by decree. The land they were given came with debts that took decades to repay."
        ),
        HistoryEvent(
            "rev08", ARENA_REVOLUTIONS, "Meiji Restoration", 1868, "Kyoto", Region.ASIA,
            "Power returned from the shogun to the emperor and Japan reorganised itself in a decade. Railways, conscription and factories arrived almost at once."
        ),
        HistoryEvent(
            "rev09", ARENA_REVOLUTIONS, "Paris Commune", 1871, "Paris", Region.EUROPE,
            "Parisians governed their own city for seventy-two days after a lost war. The suppression that ended it was brutal and fast."
        ),
        HistoryEvent(
            "rev10", ARENA_REVOLUTIONS, "Xinhai Revolution ends the Qing", 1911, "Wuchang", Region.ASIA,
            "An accidental blast in a bomb workshop triggered a mutiny that spread across the provinces. Two thousand years of imperial rule ended within months."
        ),
        HistoryEvent(
            "rev11", ARENA_REVOLUTIONS, "Mexican Constitution proclaimed", 1917, "Queretaro", Region.AMERICAS,
            "The new charter guaranteed land reform, labour rights and secular schools. It was among the most radical constitutions of its day."
        ),
        HistoryEvent(
            "rev12", ARENA_REVOLUTIONS, "October Revolution in Petrograd", 1917, "Petrograd", Region.EUROPE,
            "The Bolsheviks seized the capital key buildings in a single night. The civil war that followed lasted five years."
        ),
        HistoryEvent(
            "emp01", ARENA_EMPIRES, "Darius rules Persia at its height", -500, "Persepolis", Region.ASIA,
            "Royal roads, a standard coinage and twenty satrapies held the empire together. A message could cross it in a week."
        ),
        HistoryEvent(
            "emp02", ARENA_EMPIRES, "Alexander reaches the Indus", -326, "Taxila", Region.ASIA,
            "His army won at the Hydaspes and then refused to march any further east. He turned back and died three years later in Babylon."
        ),
        HistoryEvent(
            "emp03", ARENA_EMPIRES, "Carthage destroyed", -146, "Carthage", Region.AFRICA,
            "Rome ended the third Punic War by levelling its rival and selling the survivors. A century later Rome rebuilt the city as its own colony."
        ),
        HistoryEvent(
            "emp04", ARENA_EMPIRES, "Han envoys open the Silk Road", -130, "Chang an", Region.ASIA,
            "Imperial missions to Central Asia turned scattered caravan tracks into a trade network. Silk went west, horses and ideas came east."
        ),
        HistoryEvent(
            "emp05", ARENA_EMPIRES, "Constantinople founded", 330, "Constantinople", Region.EUROPE,
            "Constantine refounded a Greek port as a second Rome on the Bosphorus. It outlasted the original by nearly a thousand years."
        ),
        HistoryEvent(
            "emp06", ARENA_EMPIRES, "Mansa Musa reaches Cairo on pilgrimage", 1324, "Cairo", Region.AFRICA,
            "The emperor of Mali spent so much gold on the way to Mecca that Egyptian prices fell for years. European maps began marking Mali soon afterwards."
        ),
        HistoryEvent(
            "emp07", ARENA_EMPIRES, "Constantinople falls to the Ottomans", 1453, "Constantinople", Region.EUROPE,
            "Mehmed II breached the land walls with the largest cannon yet built. The Byzantine Empire ended that afternoon."
        ),
        HistoryEvent(
            "emp08", ARENA_EMPIRES, "Tenochtitlan falls", 1521, "Tenochtitlan", Region.AMERICAS,
            "A Spanish force and tens of thousands of indigenous allies besieged the Aztec capital. Smallpox had already emptied much of the city."
        ),
        HistoryEvent(
            "emp09", ARENA_EMPIRES, "Taj Mahal completed", 1653, "Agra", Region.ASIA,
            "Shah Jahan built a marble tomb for his wife over more than twenty years. It marks the peak of Mughal wealth and of Mughal spending."
        ),
        HistoryEvent(
            "emp10", ARENA_EMPIRES, "Napoleon exiled to Saint Helena", 1815, "Saint Helena", Region.AFRICA,
            "After Waterloo the British shipped him to a volcanic island in the South Atlantic. He spent six years there dictating his memoirs."
        ),
        HistoryEvent(
            "emp11", ARENA_EMPIRES, "Berlin Conference partitions Africa", 1885, "Berlin", Region.EUROPE,
            "European powers agreed rules for claiming African territory among themselves. Not one African ruler was present."
        ),
        HistoryEvent(
            "emp12", ARENA_EMPIRES, "Ottoman Empire dissolved", 1922, "Istanbul", Region.ASIA,
            "The sultanate was abolished and the last sultan left on a British warship. A republic was declared the following year."
        ),
        HistoryEvent(
            "mod01", ARENA_MODERN, "Wright brothers fly at Kitty Hawk", 1903, "Kitty Hawk", Region.AMERICAS,
            "The first powered flight covered thirty-seven metres and lasted twelve seconds. Their real breakthrough was three-axis control, not the engine."
        ),
        HistoryEvent(
            "mod02", ARENA_MODERN, "First World War begins", 1914, "Sarajevo", Region.EUROPE,
            "An assassination in Sarajevo triggered a chain of alliance commitments. Within six weeks most of Europe was mobilised."
        ),
        HistoryEvent(
            "mod03", ARENA_MODERN, "Penicillin discovered", 1928, "London", Region.EUROPE,
            "Fleming found mould killing bacteria on a dish he had left out over a holiday. It took another decade to turn the observation into a drug."
        ),
        HistoryEvent(
            "mod04", ARENA_MODERN, "Second World War ends in Europe", 1945, "Berlin", Region.EUROPE,
            "German forces surrendered unconditionally after the fall of Berlin. The Pacific war continued for three more months."
        ),
        HistoryEvent(
            "mod05", ARENA_MODERN, "Indian independence", 1947, "Delhi", Region.ASIA,
            "British rule ended and the subcontinent was partitioned into two states. The migration that followed displaced millions."
        ),
        HistoryEvent(
            "mod06", ARENA_MODERN, "Universal Declaration of Human Rights adopted", 1948, "Paris", Region.EUROPE,
            "The new United Nations agreed thirty articles on rights that belong to everyone. It is the most translated document in the world."
        ),
        HistoryEvent(
            "mod07", ARENA_MODERN, "Sputnik reaches orbit", 1957, "Baikonur", Region.ASIA,
            "A polished metal sphere the size of a beach ball circled the Earth every ninety minutes. Anyone with a shortwave radio could hear it beep."
        ),
        HistoryEvent(
            "mod08", ARENA_MODERN, "Gagarin orbits the Earth", 1961, "Baikonur", Region.ASIA,
            "The first human spaceflight lasted one orbit and one hundred and eight minutes. He ejected and parachuted down separately from the capsule."
        ),
        HistoryEvent(
            "mod09", ARENA_MODERN, "Apollo 11 lands on the Moon", 1969, "Cape Canaveral", Region.AMERICAS,
            "Two astronauts spent just over two hours walking on the Sea of Tranquility. The guidance computer had less memory than a modern doorbell."
        ),
        HistoryEvent(
            "mod10", ARENA_MODERN, "Internet protocol standardised", 1983, "Los Angeles", Region.AMERICAS,
            "Every host on the research network switched to the same addressing scheme on one day. That common protocol is what made the internet one network."
        ),
        HistoryEvent(
            "mod11", ARENA_MODERN, "Berlin Wall opens", 1989, "Berlin", Region.EUROPE,
            "A confused press conference sent crowds to the checkpoints that same evening. Guards opened the gates rather than use force."
        ),
        HistoryEvent(
            "mod12", ARENA_MODERN, "Mandela elected president", 1994, "Pretoria", Region.AFRICA,
            "South Africa held its first election open to every citizen. The man who won had been in prison for twenty-seven years."
        )
    )

    val causePairs: List<CausePair> = listOf(
        CausePair("cp01", ARENA_ANCIENT, "Grain surpluses need counting", "Scribes invent written records"),
        CausePair("cp02", ARENA_ANCIENT, "Rome expands beyond Italy", "Legions stay under arms all year"),
        CausePair("cp03", ARENA_ANCIENT, "Vesuvius erupts without warning", "A whole town is preserved in ash"),
        CausePair("cp04", ARENA_EXPLORATION, "Overland spice routes grow costly", "Captains hunt for a sea road to Asia"),
        CausePair("cp05", ARENA_EXPLORATION, "The compass reaches European ships", "Crews dare to sail out of sight of land"),
        CausePair("cp06", ARENA_EXPLORATION, "Two crowns claim the same ocean", "A treaty divides the unmapped world"),
        CausePair("cp07", ARENA_SCIENCE, "Movable type makes books cheap", "Ideas outrun the men who censor them"),
        CausePair("cp08", ARENA_SCIENCE, "Lenses are ground for spectacles", "Astronomers point them at the night sky"),
        CausePair("cp09", ARENA_SCIENCE, "Elements are weighed precisely", "A periodic pattern becomes visible"),
        CausePair("cp10", ARENA_REVOLUTIONS, "A treasury is emptied by war", "New taxes bring crowds into the streets"),
        CausePair("cp11", ARENA_REVOLUTIONS, "Printed pamphlets reach the provinces", "Local grievances become one movement"),
        CausePair("cp12", ARENA_REVOLUTIONS, "A colony earns more than its rulers", "Demands for self-government harden"),
        CausePair("cp13", ARENA_EMPIRES, "Borders stretch past supply lines", "Frontier provinces drift out of control"),
        CausePair("cp14", ARENA_EMPIRES, "Gunpowder breaches ancient walls", "Fortified capitals lose their advantage"),
        CausePair("cp15", ARENA_MODERN, "Engines become light enough to fly", "Continents sit hours apart, not weeks"),
        CausePair("cp16", ARENA_MODERN, "Rockets can reach escape velocity", "The first satellites circle the Earth")
    )
}
