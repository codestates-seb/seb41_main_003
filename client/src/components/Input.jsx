import PropType from 'prop-types';
import styles from './Input.module.css';
import { MdCheck } from 'react-icons/md';

export const CheckBox = ({ id = '', value = false, handler = () => {} }) => {
  return (
    <>
      <label className={styles.checkLabel} htmlFor={id}>
        <div className={value ? styles.checked : styles.unChecked}>
          <MdCheck />
        </div>
        <input
          className={styles.input}
          checked={value}
          onChange={() => handler(!value)}
          type="checkbox"
          name={id}
          id={id}
        />
      </label>
    </>
  );
};
CheckBox.propTypes = {
  id: PropType.string,
  value: PropType.bool,
  handler: PropType.func,
};

export const TextInput = ({
  id = '',
  placeHolder = '',
  type = 'text',
  value = '',
  handler = () => {},
  required,
}) => {
  return (
    <input
      className={styles.textInput}
      type={type}
      name={id}
      id={id}
      value={value}
      placeholder={placeHolder}
      onChange={handler}
      {...(required ? { required: required } : {})}
    />
  );
};
TextInput.propTypes = {
  id: PropType.string,
  type: PropType.string,
  placeHolder: PropType.string,
  value: PropType.string,
  handler: PropType.func,
  required: PropType.bool,
};

export const LabelTextInput = ({
  id = '',
  name = '',
  placeHolder = '',
  type = 'text',
  value = '',
  handler = () => {},
  required = false,
}) => {
  return (
    <div className={styles.container}>
      <label htmlFor={id} className={styles.textLabel}>
        {name}
        {required && <span className={styles.required} />}
      </label>
      <input
        className={styles.textInput}
        type={type}
        name={id}
        id={id}
        value={value}
        placeholder={placeHolder}
        onChange={handler}
        {...(required ? { required: required } : {})}
      />
    </div>
  );
};
LabelTextInput.propTypes = {
  id: PropType.string,
  name: PropType.string,
  type: PropType.string,
  placeHolder: PropType.string,
  value: PropType.string,
  handler: PropType.func,
  required: PropType.bool,
};

export const Textarea = ({
  id = '',
  placeHolder = '',
  type = 'text',
  value = '',
  handler = () => {},
  required = false,
}) => {
  return (
    <textarea
      className={styles.textarea}
      type={type}
      name={id}
      id={id}
      value={value}
      placeholder={placeHolder}
      onChange={handler}
      {...(required ? { required: required } : {})}
    />
  );
};
Textarea.propTypes = {
  id: PropType.string,
  type: PropType.string,
  placeHolder: PropType.string,
  value: PropType.string,
  handler: PropType.func,
  required: PropType.bool,
};
