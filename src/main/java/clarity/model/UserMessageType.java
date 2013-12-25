package clarity.model;

public enum UserMessageType {

	ACHIEVEMENT_EVENT(1),
	CLOSE_CAPTION(2),
	CLOSE_CAPTION_DIRECT(3),
	CURRENT_TIMESCALE(4),
	DESIRED_TIMESCALE(5),
	FADE(6),
	GAME_TITLE(7),
	GEIGER(8),
	HINT_TEXT(9),
	HUD_MSG(10),
	HUD_TEXT(11),
	KEY_HINT_TEXT(12),
	MESSAGE_TEXT(13),
	REQUEST_STATE(14),
	RESET_HUD(15),
	RUMBLE(16),
	SAY_TEXT(17),
	SAY_TEXT_2(18),
	SAY_TEXT_CHANNEL(19),
	SHAKE(20),
	SHAKE_DIR(21),
	STATS_CRAWL_MSG(22),
	STATS_SKIP_STATE(23),
	TEXT_MSG(24),
	TILT(25),
	TRAIN(26),
	VGUI_MENU(27),
	VOICE_MASK(28),
	VOICE_SUBTITLE(29),
	SEND_AUDIO(30),
	MAX__BASE(63),
	ADD_UNIT_TO_SELECTION(64),
	A_I_DEBUG_LINE(65),
	CHAT_EVENT(66),
	COMBAT_HERO_POSITIONS(67),
	COMBAT_LOG_DATA(68),
	COMBAT_LOG_SHOW_DEATH(70),
	CREATE_LINEAR_PROJECTILE(71),
	DESTROY_LINEAR_PROJECTILE(72),
	DODGE_TRACKING_PROJECTILES(73),
	GLOBAL_LIGHT_COLOR(74),
	GLOBAL_LIGHT_DIRECTION(75),
	INVALID_COMMAND(76),
	LOCATION_PING(77),
	MAP_LINE(78),
	MINI_KILL_CAM_INFO(79),
	MINIMAP_DEBUG_POINT(80),
	MINIMAP_EVENT(81),
	NEVERMORE_REQUIEM(82),
	OVERHEAD_EVENT(83),
	SET_NEXT_AUTOBUY_ITEM(84),
	SHARED_COOLDOWN(85),
	SPECTATOR_PLAYER_CLICK(86),
	TUTORIAL_TIP_INFO(87),
	UNIT_EVENT(88),
	PARTICLE_MANAGER(89),
	BOT_CHAT(90),
	HUD_ERROR(91),
	ITEM_PURCHASED(92),
	PING(93),
	ITEM_FOUND(94),
	CHARACTER_SPEAK_CONCEPT(95),
	SWAP_VERIFY(96),
	WORLD_LINE(97),
	TOURNAMENT_DROP(98),
	ITEM_ALERT(99),
	HALLOWEEN_DROPS(100),
	CHAT_WHEEL(101),
	RECEIVED_XMAS_GIFT(102),
	UPDATE_SHARED_CONTENT(103),
	TUTORIAL_REQUEST_EXP(104),
	TUTORIAL_PING_MINIMAP(105),
	GAMERULES_STATE_CHANGED(106),
	SHOW_SURVEY(107),
	TUTORIAL_FADE(108),
	ADD_QUEST_LOG_ENTRY(109),
	SEND_STAT_POPUP(110),
	TUTORIAL_FINISH(111),
	SEND_ROSHAN_POPUP(112),
	SEND_GENERIC_TOOL_TIP(113),
	SEND_FINAL_GOLD(114),
	CUSTOM_MSG(115),
	COACH_HUD_PING(116),
	CLIENT_LOAD_GRID_NAV(117);
	
	private final int id;

	private UserMessageType(int id) {
		this.id = id;
	}

}