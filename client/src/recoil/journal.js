import { atom } from 'recoil';
import dayjs from 'dayjs';

const localStorageEffect =
  (key) =>
  ({ setSelf, onSet }) => {
    const savedValue = localStorage.getItem(key);
    if (savedValue != null) {
      setSelf(JSON.parse(savedValue));
    }

    onSet((newValue, _, isReset) => {
      isReset
        ? localStorage.removeItem(key)
        : localStorage.setItem(key, JSON.stringify(newValue));
    });
  };

const ChangeJournal = atom({
  key: 'ChangeJournal',
  default: {
    dateNoticeTitle: '',
    startTime: dayjs()
      .set('hour', 9)
      .set('minute', 0)
      .format('YYYY-MM-DD HH:mm'),
    endTime: dayjs()
      .set('hour', 18)
      .set('minute', 0)
      .format('YYYY-MM-DD HH:mm'),
    scheduleBody: '',
    noticeBody: '',
    Homeworks: [],
  },
  effects: [localStorageEffect('journal')],
});

export const IsChangeJournal = atom({
  key: 'IsChangeJournal',
  default: false,
  effects: [localStorageEffect('is_change_journal')],
});

export default ChangeJournal;
