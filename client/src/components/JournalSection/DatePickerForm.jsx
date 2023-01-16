/* eslint-disable react/display-name */
import styles from './DatePickerForm.module.css';
import { useState, useEffect, forwardRef } from 'react';
import DatePicker from 'react-datepicker';
import PropTypes from 'prop-types';
import { VscTriangleDown } from 'react-icons/vsc';
import { ko } from 'date-fns/esm/locale';
import 'react-datepicker/dist/react-datepicker.css';
import './DatePicker.css';

const DatePickerForm = ({ user, setUser }) => {
  const [isTimePopup, setIsTimePopup] = useState(false);
  const [date, setDate] = useState(new Date(user.startTime));
  const [sTime, setStartTime] = useState(new Date(user.startTime));
  const [eTime, setEndTime] = useState(new Date(user.endTime));

  useEffect(() => {
    const start = new Date(sTime).getTime();
    const end = new Date(eTime).getTime();

    if (start > end) setEndTime(new Date(start + 900000));

    const selectDate = [date.getFullYear(), date.getMonth(), date.getDate()];

    const startTime = new Date(
      ...selectDate,
      new Date(sTime).getHours(),
      new Date(sTime).getMinutes()
    ).toISOString();

    const endTime = new Date(
      ...selectDate,
      new Date(eTime).getHours(),
      new Date(eTime).getMinutes()
    ).toISOString();

    setUser({ ...user, startTime, endTime });
  }, [date, sTime, eTime]);

  const DateButton = forwardRef(({ onClick }, ref) => (
    <button ref={ref} className={styles.iconButton} onClick={onClick}>
      <div className={styles.day}>
        <p className={styles.font1}>{date.getDate().toLocaleString()}</p>
        <VscTriangleDown size="24px" />
      </div>
      <div className={styles.monthYear}>
        <p className={styles.font4}>
          {date.getFullYear().toString()}년 {(date.getMonth() + 1).toString()}월
        </p>
      </div>
    </button>
  ));

  const TimeButton = forwardRef(({ onClick, time }, ref) => (
    <button ref={ref} className={styles.iconButtonT} onClick={onClick}>
      <div className={styles.time}>
        <div className={styles.font6}>
          {new Date(time).toTimeString().slice(0, 5)}{' '}
          <VscTriangleDown size="8px" />
        </div>
      </div>
    </button>
  ));
  TimeButton.propTypes = {
    time: PropTypes.object,
  };

  const filterPassedTime = (time) => {
    const current = new Date(sTime);
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
            onChange={(date) => setStartTime(date)}
            selected={sTime}
            showTimeSelect
            showTimeSelectOnly
            timeIntervals={15}
            dateFormat="h:mm"
            customInput={<TimeButton time={sTime} />}
            timeCaption="시작 시간"
            locale={ko}
          />
        </div>
        <div>
          <span>종료 시간</span>
          <DatePicker
            showPopperArrow={false}
            onChange={(date) => setEndTime(date)}
            selected={eTime}
            showTimeSelect
            showTimeSelectOnly
            timeIntervals={15}
            dateFormat="h:mm"
            customInput={<TimeButton time={eTime} />}
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
        onChange={(date) => setDate(date)}
        selected={date}
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
            {new Date(sTime).toTimeString().slice(0, 5)}~
            {new Date(eTime).toTimeString().slice(0, 5)}
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
  user: PropTypes.object,
  setUser: PropTypes.func,
};
export default DatePickerForm;
