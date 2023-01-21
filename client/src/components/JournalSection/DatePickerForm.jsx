/* eslint-disable react/display-name */
import styles from './DatePickerForm.module.css';
import { useState, useEffect, forwardRef } from 'react';
import DatePicker from 'react-datepicker';
import PropTypes from 'prop-types';
import { VscTriangleDown } from 'react-icons/vsc';
import { ko } from 'date-fns/esm/locale';
import 'react-datepicker/dist/react-datepicker.css';
import './DatePicker.css';
import { useRecoilState } from 'recoil';
import ChangeJournal from '../../recoil/journal';
import dayjs from 'dayjs';

const DatePickerForm = () => {
  const [userData, setUserData] = useRecoilState(ChangeJournal);
  const [isTimePopup, setIsTimePopup] = useState(false);

  useEffect(() => {
    const start = dayjs(userData.startTime);
    const end = dayjs(userData.endTime);

    if (start > end)
      setUserData({
        ...userData,
        endTime: dayjs(userData.endTime)
          .add(30, 'minute')
          .format('YYYY-MM-DD HH:mm'),
      });
  }, [userData]);

  const DateButton = forwardRef(({ onClick }, ref) => (
    <button ref={ref} className={styles.iconButton} onClick={onClick}>
      <div className={styles.day}>
        <p className={styles.font1}>{dayjs(userData.startTime).date()}</p>
        <VscTriangleDown size="24px" />
      </div>
      <div className={styles.monthYear}>
        <p className={styles.font4}>
          {dayjs(userData.startTime).format('YYYY년 M월')}
        </p>
      </div>
    </button>
  ));

  const TimeButton = forwardRef(({ onClick, time }, ref) => (
    <button ref={ref} className={styles.iconButtonT} onClick={onClick}>
      <div className={styles.time}>
        <div className={styles.font6}>
          {dayjs(time).format('HH:mm')} <VscTriangleDown size="8px" />
        </div>
      </div>
    </button>
  ));
  TimeButton.propTypes = {
    time: PropTypes.string,
  };

  const filterPassedTime = (time) => {
    const current = new Date(userData.startTime);
    const selected = new Date(time);

    return current.getTime() < selected.getTime();
  };

  const TimePopup = () => {
    return (
      <div className={styles.timePopup}>
        <div>
          <span>시작 시간</span>
          <DatePicker
            showPopperArrow={false}
            onChange={(date) => {
              setUserData({
                ...userData,
                startTime: `${dayjs(userData.startTime).format(
                  'YYYY-MM-DD'
                )} ${dayjs(date).format('HH:mm')}`,
              });
            }}
            selected={new Date(userData.startTime)}
            showTimeSelect
            showTimeSelectOnly
            timeIntervals={15}
            dateFormat="h:mm"
            customInput={<TimeButton time={userData.startTime} />}
            timeCaption="시작 시간"
            locale={ko}
          />
        </div>
        <div>
          <span>종료 시간</span>
          <DatePicker
            showPopperArrow={false}
            onChange={(date) => {
              setUserData({
                ...userData,
                endTime: `${dayjs(userData.startTime).format(
                  'YYYY-MM-DD'
                )} ${dayjs(date).format('HH:mm')}`,
              });
            }}
            selected={new Date(userData.endTime)}
            showTimeSelect
            showTimeSelectOnly
            timeIntervals={15}
            dateFormat="h:mm"
            customInput={<TimeButton time={userData.endTime} />}
            timeCaption="종료 시간"
            locale={ko}
            filterTime={filterPassedTime}
          />
        </div>
      </div>
    );
  };

  return (
    <div className={styles.container}>
      <DatePicker
        id="date"
        showPopperArrow={false}
        className={styles.calendar}
        onChange={(date) => {
          setUserData({
            ...userData,
            startTime: `${dayjs(date).format('YYYY-MM-DD')} ${dayjs(
              userData.startTime
            ).format('HH:mm')}`,
            endTime: `${dayjs(date).format('YYYY-MM-DD')} ${dayjs(
              userData.endTime
            ).format('HH:mm')}`,
          });
        }}
        selected={new Date(userData.startTime)}
        dateFormat="h:mm aa"
        customInput={<DateButton />}
        locale={ko}
      />
      <button
        className={styles.iconButtonT}
        onClick={() => setIsTimePopup(!isTimePopup)}
      >
        <div className={styles.time}>
          <div className={styles.font6}>
            {dayjs(userData.startTime).format('HH:mm')}~
            {dayjs(userData.endTime).format('HH:mm')}
          </div>
          <VscTriangleDown size="12px" />
        </div>
      </button>
      {isTimePopup && <TimePopup />}
    </div>
  );
};
DatePickerForm.propTypes = {
  onClick: PropTypes.func,
  selDate: PropTypes.object,
  setSelDate: PropTypes.func,
};
export default DatePickerForm;
