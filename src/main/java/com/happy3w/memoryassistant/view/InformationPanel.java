package com.happy3w.memoryassistant.view;

import com.happy3w.footstone.exception.InputException;
import com.happy3w.footstone.ui.AbstractDataPanel;
import com.happy3w.memoryassistant.model.MAInformation;
import com.happy3w.memoryassistant.model.MAKeyword;
import com.happy3w.memoryassistant.view.res.ResConst;
import com.happy3w.memoryassistant.view.wgt.KeywordField;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class InformationPanel extends AbstractDataPanel<MAInformation> {
    private static Logger logger = Logger.getLogger(InformationPanel.class);
    private KeywordField txtKeys;
    private JTextPane txtInfoDetail;

    public InformationPanel() {
        initUI();
        makeDropable();
    }

    private void makeDropable() {
        new DropTarget(this.txtInfoDetail, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent event) {
                doAcceptDropFile(event);
            }

        });
    }

    private void doAcceptDropFile(DropTargetDropEvent event) {
        StringBuilder buffer = new StringBuilder(this.txtInfoDetail.getText());
        int carePos = txtInfoDetail.getCaretPosition();
        boolean isFirstFile = true;

        //接受复制操作
        event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        //获取拖放的内容  
        Transferable transferable = event.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        //遍历拖放内容里的所有数据格式
        for (int i = 0; i < flavors.length; i++) {
            DataFlavor d = flavors[i];
            //如果拖放内容的数据格式是文件列表  
            if (d.equals(DataFlavor.javaFileListFlavor)) {
                try {
                    //取出拖放操作里的文件列表  
                    List fileList = (List) transferable.getTransferData(d);
                    for (Object f : fileList) {
                        File file = (File) f;
                        String fileName = file.getAbsolutePath();
                        if (!isFirstFile) {
                            buffer.insert(carePos, '\n');
                            ++carePos;
                        }
                        isFirstFile = false;

                        buffer.insert(carePos, fileName);
                        carePos += fileName.length();
                    }
                } catch (UnsupportedFlavorException e) {
                    logger.trace(e);
                } catch (IOException e) {
                    logger.trace(e);
                }
            }
            //强制拖放操作结束，停止阻塞拖放源  
            event.dropComplete(true);
        }
        this.txtInfoDetail.setText(buffer.toString());
    }

    private void initUI() {
        this.setLayout(new GridBagLayout());
        this.add(new JLabel("Key:"),
                new GridBagConstraints(0, 0, 1, 1,
                        0, 0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        txtKeys = new KeywordField();
        this.add(txtKeys,
                new GridBagConstraints(1, 0, 1, 1,
                        1, 0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                        new Insets(0, 12, 0, 0), 0, 0));

        txtInfoDetail = new JTextPane();
//		txtInfoDetail.setLineWrap(true);
        this.add(new JScrollPane(txtInfoDetail),
                new GridBagConstraints(0, 1, 2, 1,
                        1, 1,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                        new Insets(12, 0, 0, 0), 0, 0));
    }

    @Override
    public void showData(MAInformation value) {
        if (value == null) {
            txtKeys.setLstKeyword(null);
            txtInfoDetail.setText(null);
            return;
        }

        StringBuilder keyBuilder = new StringBuilder();
        for (MAKeyword keyword : value.getLstKeyword()) {
            if (keyBuilder.length() != 0) {
                keyBuilder.append(' ');
            }
            keyBuilder.append(keyword.getKeyword());
        }
        this.txtKeys.setLstKeyword(value.getLstKeyword());
        this.txtInfoDetail.setText(value.getContent());
    }

    @Override
    public void collectData(MAInformation value) {
        value.setLstKeyword(txtKeys.getLstKeyword());
        value.setContent(this.txtInfoDetail.getText());
        if (this.oldValue != null) {
            value.setId(oldValue.getId());
            value.setCreatedTime(oldValue.getCreatedTime());
        }
    }

    @Override
    public void verifyInput() throws InputException {
        if (txtKeys.getLstKeyword().isEmpty()) {
            throw new InputException(ResConst.ResKey, ResConst.NotEmpty, new Object[]{"Keyword"}, txtKeys, null);
        }
    }

}
