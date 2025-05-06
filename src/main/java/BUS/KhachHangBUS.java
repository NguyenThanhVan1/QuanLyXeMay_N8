/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

/**
 *
 * @author lekha
 */
import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import java.util.ArrayList;
public class KhachHangBUS 
{
    private ArrayList<KhachHangDTO> dskh;
    public KhachHangBUS(int i1)
    {
        listKH();
    }
    public KhachHangBUS(){
    }
    public KhachHangDTO get(String MaKH)
    {
        for(KhachHangDTO kh : dskh )
        {
            if(kh.getMakh().equals(MaKH))
            {
                return kh;
            }
        }
        return null;
    }
    public void listKH()
    {
        KhachHangDAO khDAO = new KhachHangDAO();
        dskh = new ArrayList<>();
        dskh = khDAO.list();
    }
    public void addKH(KhachHangDTO sp)
    {
        dskh.add(sp);
        KhachHangDAO khDAO = new KhachHangDAO();
        khDAO.add(sp);
    }

    public void deleteKH(String MaKH)
    {
        for(KhachHangDTO kh : dskh )
        {
            if(kh.getMakh().equals(MaKH))
            {
                dskh.remove(kh);
                KhachHangDAO khDAO = new KhachHangDAO();
                khDAO.delete(MaKH);
                return;
            }
        }
    }
    public void setKH(KhachHangDTO s)
    {
        for(int i = 0 ; i < dskh.size() ; i++)
        {
            if(dskh.get(i).getMakh().equals(s.getMakh()))
            {
                dskh.set(i, s);
                KhachHangDAO khDAO = new KhachHangDAO();
                khDAO.set(s);
                return;
            }
        }
    }
    public boolean check(String makh)
    {
        for(KhachHangDTO kh : dskh)
        {
            if(kh.getMakh().equals(makh))
            {
                return true;
            }
        }
        return false;
    }
    public ArrayList<KhachHangDTO> search(String makh,String ten,String dc)
    {
        ArrayList<KhachHangDTO> search = new ArrayList<>();
        makh = makh.isEmpty()?makh = "": makh;
        ten = ten.isEmpty()?ten = "": ten;
        dc = dc.isEmpty()?dc = "": dc;
        for(KhachHangDTO kh : dskh)
        {
            if( kh.getMakh().contains(makh) && 
                kh.getHoten()   .contains(ten) &&
                kh.getDiachi().contains(dc))
            {
                search.add(kh);
            }
        }
        return search;
    }
    public ArrayList<KhachHangDTO> getList() {
        return dskh;
    }
}
