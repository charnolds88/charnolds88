/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 */
package quest.cygnea;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author pralinka
 */
public class _15001LendingBothHands extends QuestHandler {

	public static final int questId = 15001;

	public _15001LendingBothHands() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(804698).addOnQuestStart(questId);
		qe.registerQuestNpc(804698).addOnTalkEvent(questId);
		qe.registerQuestNpc(235790).addOnKillEvent(questId);
		qe.registerQuestNpc(235791).addOnKillEvent(questId);
		qe.registerQuestNpc(235799).addOnKillEvent(questId);
		qe.registerQuestNpc(235800).addOnKillEvent(questId);
	}

	@Override
	public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (var == 0) {
				int[] varans = { 235790, 235791 };
				int[] lizards = { 235799, 235800 };
				int targetId = env.getTargetId();
				int var1 = qs.getQuestVarById(1);
				int var2 = qs.getQuestVarById(2);
				switch (targetId) {
					case 235790:
					case 235791: {
						if (var1 < 4) {
							return defaultOnKillEvent(env, varans, 0, 4, 1);
						}
						else if (var1 == 4) {
							if (var2 == 5) {
								qs.setQuestVar(1);
								qs.setStatus(QuestStatus.REWARD);
								updateQuestStatus(env);
								return true;
							}
							else {
								return defaultOnKillEvent(env, varans, 4, 5, 1);
							}
						}
						break;
					}
					case 235799:
					case 235800: {
						if (var2 < 4) {
							return defaultOnKillEvent(env, lizards, 0, 4, 2);
						}
						else if (var2 == 4) {
							if (var1 == 5) {
								qs.setQuestVar(1);
								qs.setStatus(QuestStatus.REWARD);
								updateQuestStatus(env);
								return true;
							}
							else {
								return defaultOnKillEvent(env, lizards, 4, 5, 2);
							}
						}
						break;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		DialogAction dialog = env.getDialog();

		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 804698) {
				if (dialog == DialogAction.QUEST_SELECT) {
					return sendQuestDialog(env, 4762);
				}
				else {
					return sendQuestStartDialog(env);
				}
			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 804698) {
				if (dialog == DialogAction.QUEST_SELECT) {
					return sendQuestDialog(env, 2034);
				}
				else {
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}
}
